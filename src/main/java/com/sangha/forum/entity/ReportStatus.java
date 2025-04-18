package com.sangha.forum.entity;

public enum ReportStatus {
    PENDING,      // Report is waiting for moderator review
    UNDER_REVIEW, // Report is being reviewed by a moderator
    RESOLVED,     // Report has been resolved
    DISMISSED,    // Report has been dismissed
    INVALID       // Report is invalid or spam
} 