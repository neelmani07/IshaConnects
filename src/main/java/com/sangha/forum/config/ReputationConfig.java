package com.sangha.forum.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ReputationConfig {
    // Post-related points
    public static final int POST_CREATION_POINTS = 10;
    public static final int POST_UPVOTE_POINTS = 2;
    public static final int POST_DOWNVOTE_POINTS = -1;
    public static final int POST_DELETION_POINTS = -5;

    // Comment-related points
    public static final int COMMENT_CREATION_POINTS = 5;
    public static final int COMMENT_UPVOTE_POINTS = 1;
    public static final int COMMENT_DOWNVOTE_POINTS = -1;
    public static final int COMMENT_DELETION_POINTS = -3;

    // Answer-related points
    public static final int ANSWER_ACCEPTED_POINTS = 15;
    public static final int ANSWER_UNMARKED_POINTS = -15;
} 