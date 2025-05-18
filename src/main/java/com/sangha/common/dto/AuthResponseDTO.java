package com.sangha.common.dto;

import com.sangha.common.entity.UserRole;

public class AuthResponseDTO {
    private String token;
    private String email;
    private UserRole role;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, String email, UserRole role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
} 