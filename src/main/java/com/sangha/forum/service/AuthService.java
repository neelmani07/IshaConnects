package com.sangha.forum.service;

import com.sangha.forum.dto.AuthRequestDTO;
import com.sangha.forum.dto.AuthResponseDTO;
import com.sangha.forum.dto.PasswordChangeDTO;
import com.sangha.forum.dto.PasswordResetDTO;
import com.sangha.forum.entity.UserRole;
import com.sangha.forum.exception.BadRequestException;
import com.sangha.forum.exception.ResourceNotFoundException;

public interface AuthService {
    // Authentication
    AuthResponseDTO authenticate(AuthRequestDTO request);
    AuthResponseDTO register(AuthRequestDTO request);
    void logout(String token);
    
    // Password Management
    void requestPasswordReset(String email) throws ResourceNotFoundException;
    void resetPassword(PasswordResetDTO request) throws BadRequestException;
    void changePassword(Long userId, PasswordChangeDTO request) throws BadRequestException;
    void validatePassword(String password) throws BadRequestException;
    
    // Role Management
    void assignRole(Long userId, UserRole role) throws ResourceNotFoundException;
    void revokeRole(Long userId, UserRole role) throws ResourceNotFoundException;
    boolean hasRole(Long userId, UserRole role);
    boolean hasAnyRole(Long userId, UserRole... roles);
    
    // Account Management
    void lockAccount(Long userId) throws ResourceNotFoundException;
    void unlockAccount(Long userId) throws ResourceNotFoundException;
    boolean isAccountLocked(Long userId);
    void incrementFailedAttempts(Long userId) throws ResourceNotFoundException;
    void resetFailedAttempts(Long userId) throws ResourceNotFoundException;
} 