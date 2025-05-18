package com.sangha.forum.service.impl;

import com.sangha.forum.dto.AuthRequestDTO;
import com.sangha.forum.dto.AuthResponseDTO;
import com.sangha.forum.dto.PasswordChangeDTO;
import com.sangha.forum.dto.PasswordResetDTO;
import com.sangha.forum.entity.ContactDetails;
import com.sangha.forum.entity.UserRole;
import com.sangha.forum.exception.BadRequestException;
import com.sangha.forum.exception.ResourceNotFoundException;
import com.sangha.forum.repository.ContactDetailsRepository;
import com.sangha.forum.security.JwtTokenProvider;
import com.sangha.forum.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_TIME_DURATION = 30; // minutes

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            
            ContactDetails user = contactDetailsRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            
            resetFailedAttempts(user.getId());
            
            return new AuthResponseDTO(token, user.getEmail(), user.getRole());
        } catch (Exception e) {
            ContactDetails user = contactDetailsRepository.findByEmail(request.getEmail())
                .orElse(null);
            if (user != null) {
                incrementFailedAttempts(user.getId());
            }
            throw new BadRequestException("Invalid email or password");
        }
    }

    @Override
    @Transactional
    public AuthResponseDTO register(AuthRequestDTO request) {
        if (contactDetailsRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already taken");
        }

        validatePassword(request.getPassword());

        ContactDetails user = new ContactDetails();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setFailedAttempt(0);
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        user.setPasswordHistory(new HashSet<>());
        user.getPasswordHistory().add(user.getPassword());

        contactDetailsRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthResponseDTO(token, user.getEmail(), user.getRole());
    }

    @Override
    public void logout(String token) {
        // In a real implementation, you might want to blacklist the token
        SecurityContextHolder.clearContext();
    }

    @Override
    public void requestPasswordReset(String email) {
        ContactDetails user = contactDetailsRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        contactDetailsRepository.save(user);

        // Send email with reset link
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n\n" +
            "http://yourdomain.com/reset-password?token=" + token);
        mailSender.send(message);
    }

    @Override
    @Transactional
    public void resetPassword(PasswordResetDTO request) {
        ContactDetails user = contactDetailsRepository.findByResetToken(request.getToken())
            .orElseThrow(() -> new BadRequestException("Invalid or expired reset token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Reset token has expired");
        }

        validatePassword(request.getNewPassword());
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        if (user.getPasswordHistory().contains(encodedPassword)) {
            throw new BadRequestException("Cannot reuse any of your last passwords");
        }

        user.setPassword(encodedPassword);
        user.getPasswordHistory().add(encodedPassword);
        if (user.getPasswordHistory().size() > 5) {
            user.getPasswordHistory().remove(user.getPasswordHistory().iterator().next());
        }
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        contactDetailsRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, PasswordChangeDTO request) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        validatePassword(request.getNewPassword());
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        if (user.getPasswordHistory().contains(encodedPassword)) {
            throw new BadRequestException("Cannot reuse any of your last passwords");
        }

        user.setPassword(encodedPassword);
        user.getPasswordHistory().add(encodedPassword);
        if (user.getPasswordHistory().size() > 5) {
            user.getPasswordHistory().remove(user.getPasswordHistory().iterator().next());
        }
        contactDetailsRepository.save(user);
    }

    @Override
    public void validatePassword(String password) {
        if (password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new BadRequestException("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new BadRequestException("Password must contain at least one number");
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new BadRequestException("Password must contain at least one special character");
        }
    }

    @Override
    @Transactional
    public void assignRole(Long userId, UserRole role) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(role);
        contactDetailsRepository.save(user);
    }

    @Override
    @Transactional
    public void revokeRole(Long userId, UserRole role) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getRole() == role) {
            user.setRole(UserRole.USER); // Default to USER role
            contactDetailsRepository.save(user);
        }
    }

    @Override
    public boolean hasRole(Long userId, UserRole role) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getRole() == role;
    }

    @Override
    public boolean hasAnyRole(Long userId, UserRole... roles) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        for (UserRole role : roles) {
            if (user.getRole() == role) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void lockAccount(Long userId) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());
        contactDetailsRepository.save(user);
    }

    @Override
    @Transactional
    public void unlockAccount(Long userId) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        user.setFailedAttempt(0);
        contactDetailsRepository.save(user);
    }

    @Override
    public boolean isAccountLocked(Long userId) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return !user.isAccountNonLocked();
    }

    @Override
    @Transactional
    public void incrementFailedAttempts(Long userId) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        int newFailCount = user.getFailedAttempt() + 1;
        user.setFailedAttempt(newFailCount);
        
        if (newFailCount >= MAX_FAILED_ATTEMPTS) {
            user.setAccountNonLocked(false);
            user.setLockTime(LocalDateTime.now());
        }
        contactDetailsRepository.save(user);
    }

    @Override
    @Transactional
    public void resetFailedAttempts(Long userId) {
        ContactDetails user = contactDetailsRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setFailedAttempt(0);
        contactDetailsRepository.save(user);
    }
} 