package com.sangha.forum.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.Mention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MentionService {
    void processMentions(String content, Post post, Comment comment);
    Page<Mention> getUnreadMentions(ContactDetails user, Pageable pageable);
    void markAsRead(Long mentionId, ContactDetails user);
    void markAllAsRead(ContactDetails user);
    List<Mention> getMentionsForPost(Long postId);
    List<Mention> getMentionsForComment(Long commentId);
} 