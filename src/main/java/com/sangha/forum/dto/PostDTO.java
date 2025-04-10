package com.sangha.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long createdAt;
    private Long updatedAt;
} 