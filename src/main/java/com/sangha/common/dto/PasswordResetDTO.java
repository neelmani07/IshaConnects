package com.sangha.common.dto;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String token;
    private String newPassword;
    private String confirmPassword;
} 