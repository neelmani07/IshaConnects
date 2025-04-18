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
public class ReportRequestDTO {
    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotBlank(message = "Reason is required")
    private String reason;
} 