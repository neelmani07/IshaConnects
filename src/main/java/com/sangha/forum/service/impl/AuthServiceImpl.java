package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.AuthRequestDTO;
import com.sangha.forum.dto.AuthResponseDTO;
import com.sangha.forum.entity.UserRole;
import com.sangha.forum.security.JwtUtils;
import com.sangha.forum.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ContactDetailsService contactDetailsService;

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);
        
        return AuthResponseDTO.builder()
                .token(token)
                .email(userDetails.getUsername())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();
    }

    @Override
    public AuthResponseDTO register(AuthRequestDTO request) {
        ContactDetails user = new ContactDetails();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        
        ContactDetails savedUser = contactDetailsService.save(user);
        
        String token = jwtUtils.generateToken(savedUser);
        
        return AuthResponseDTO.builder()
                .token(token)
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }
} 