package com.sangha.forum.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String type; // "MENTION", "UPVOTE", "ACCEPTED_ANSWER", "COMMENT"
    private String message;
    private Long postId;
    private Long commentId;
    private String mentionedBy; // or the user who performed the action
    private LocalDateTime createdAt;
    private boolean isRead;
}