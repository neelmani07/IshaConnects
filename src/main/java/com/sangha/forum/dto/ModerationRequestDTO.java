package com.sangha.forum.dto;

import com.sangha.forum.entity.ModerationStatus;
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
public class ModerationRequestDTO {
    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotNull(message = "Status is required")
    private ModerationStatus status;

    private String reason;
} 