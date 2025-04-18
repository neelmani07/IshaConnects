package com.sangha.forum.entity;

public enum ModerationStatus {
    PENDING,    // Post is waiting for moderation
    APPROVED,   // Post has been approved
    REJECTED,   // Post has been rejected
    FLAGGED,    // Post has been flagged by users
    UNDER_REVIEW // Post is under review by moderators
} 