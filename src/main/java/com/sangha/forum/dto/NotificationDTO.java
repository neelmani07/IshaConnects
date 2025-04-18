package com.sangha.forum.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String type; // "MENTION"
    private String message;
    private Long postId;
    private Long commentId;
    private String mentionedBy;
    private LocalDateTime createdAt;
    private boolean isRead;
} 