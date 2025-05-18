package com.sangha.common.service;

import com.sangha.common.dto.AuthRequestDTO;
import com.sangha.common.dto.AuthResponseDTO;
import com.sangha.common.dto.PasswordChangeDTO;
import com.sangha.common.dto.PasswordResetDTO;
import com.sangha.common.entity.ContactDetails;
import com.sangha.common.entity.UserRole;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO request);
    AuthResponseDTO register(AuthRequestDTO request);
    void logout(String token);
    void requestPasswordReset(String email);
    void resetPassword(PasswordResetDTO request);
    void changePassword(Long userId, PasswordChangeDTO request);
    void validatePassword(String password);
    void assignRole(Long userId, UserRole role);
    void revokeRole(Long userId, UserRole role);
    boolean hasRole(Long userId, UserRole role);
    boolean hasAnyRole(Long userId, UserRole... roles);
    void lockAccount(Long userId);
    void unlockAccount(Long userId);
    boolean isAccountLocked(Long userId);
    void incrementFailedAttempts(Long userId);
    void resetFailedAttempts(Long userId);
} 