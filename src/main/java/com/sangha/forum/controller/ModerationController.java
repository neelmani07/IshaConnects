package com.sangha.forum.controller;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.dto.ModerationRequestDTO;
import com.sangha.forum.entity.PostModeration;
import com.sangha.forum.service.ModerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderation")
@RequiredArgsConstructor
@Tag(name = "Moderation", description = "Post moderation APIs")
@PreAuthorize("hasRole('MODERATOR')")
public class ModerationController {

    private final ModerationService moderationService;

    @PostMapping
    @Operation(summary = "Moderate a post")
    public ResponseEntity<PostModeration> moderatePost(
            @RequestBody ModerationRequestDTO request,
            @AuthenticationPrincipal ContactDetails moderator) {
        return ResponseEntity.ok(moderationService.moderatePost(request, moderator));
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Get moderation history for a post")
    public ResponseEntity<List<PostModeration>> getModerationHistory(@PathVariable Long postId) {
        return ResponseEntity.ok(moderationService.getModerationHistory(postId));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending moderations")
    public ResponseEntity<Page<PostModeration>> getPendingModerations(Pageable pageable) {
        return ResponseEntity.ok(moderationService.getPendingModerations(pageable));
    }

    @GetMapping("/moderator/{moderatorId}")
    @Operation(summary = "Get moderations by moderator")
    public ResponseEntity<Page<PostModeration>> getModeratedByUser(
            @PathVariable Long moderatorId,
            Pageable pageable) {
        return ResponseEntity.ok(moderationService.getModeratedByUser(moderatorId, pageable));
    }
} 