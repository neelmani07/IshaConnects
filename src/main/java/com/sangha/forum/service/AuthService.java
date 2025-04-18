package com.sangha.forum.service;

import com.sangha.forum.dto.AuthRequestDTO;
import com.sangha.forum.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO request);
    AuthResponseDTO register(AuthRequestDTO request);
} 