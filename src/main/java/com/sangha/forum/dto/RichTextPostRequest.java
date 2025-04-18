package com.sangha.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichTextPostRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "HTML content is required")
    private String contentHtml;

    @NotBlank(message = "Plain text content is required")
    private String contentPlain;

    private String contentPreview;
    private String editorVersion;
    private String imageUrl;
} 