package com.sangha.forum.controller;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.Mention;
import com.sangha.forum.service.MentionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentions")
@Tag(name = "Mentions", description = "APIs for managing user mentions")
public class MentionController {

    @Autowired
    private MentionService mentionService;

    @GetMapping("/unread")
    @Operation(summary = "Get unread mentions for the current user")
    public ResponseEntity<Page<Mention>> getUnreadMentions(
            @AuthenticationPrincipal ContactDetails user,
            Pageable pageable) {
        return ResponseEntity.ok(mentionService.getUnreadMentions(user, pageable));
    }

    @PostMapping("/{id}/read")
    @Operation(summary = "Mark a mention as read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal ContactDetails user) {
        mentionService.markAsRead(id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/read-all")
    @Operation(summary = "Mark all mentions as read")
    public ResponseEntity<Void> markAllAsRead(
            @AuthenticationPrincipal ContactDetails user) {
        mentionService.markAllAsRead(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Get all mentions in a post")
    public ResponseEntity<List<Mention>> getPostMentions(@PathVariable Long postId) {
        return ResponseEntity.ok(mentionService.getMentionsForPost(postId));
    }

    @GetMapping("/comment/{commentId}")
    @Operation(summary = "Get all mentions in a comment")
    public ResponseEntity<List<Mention>> getCommentMentions(@PathVariable Long commentId) {
        return ResponseEntity.ok(mentionService.getMentionsForComment(commentId));
    }
} 