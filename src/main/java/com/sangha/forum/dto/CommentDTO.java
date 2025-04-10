package com.sangha.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    
    @NotBlank(message = "Content is required")
    @Size(min = 1, message = "Content must not be empty")
    private String content;
    
    private Long postId;
    private Long userId;
    private Long createdAt;
    private Long updatedAt;
} 