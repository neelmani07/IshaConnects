package com.sangha.forum.dto;

import lombok.Data;

@Data
public class UserSuggestionDTO {
    private Long id;
    private String username;
    private String displayName;
    private String avatarUrl;
} 