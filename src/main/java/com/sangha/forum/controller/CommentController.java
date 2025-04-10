package com.sangha.forum.controller;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.common.dto.ApiResponse;
import com.sangha.forum.dto.CommentDTO;
import com.sangha.forum.entity.Comment;
import com.sangha.forum.service.CommentService;
import com.sangha.connect.service.ContactDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment", description = "Comment management APIs")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ContactDetailsService contactDetailsService;

    @Operation(summary = "Get all comments")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Comment>>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(ApiResponse.success("Comments retrieved successfully", comments));
    }

    @Operation(summary = "Get comment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Comment>> getCommentById(@Parameter(description = "Comment ID") @PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(ApiResponse.success("Comment retrieved successfully", comment));
    }

    @Operation(summary = "Get comments by post ID")
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<Comment>>> getCommentsByPostId(@Parameter(description = "Post ID") @PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(ApiResponse.success("Comments retrieved successfully", comments));
    }

    @Operation(summary = "Create a new comment")
    @PostMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<Comment>> createComment(
            @Parameter(description = "Post ID") @PathVariable Long postId,
            @Parameter(description = "Comment details") @RequestBody CommentDTO commentDTO,
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Parent comment ID (optional)") @RequestParam(required = false) Long parentCommentId) {
        ContactDetails createdBy = contactDetailsService.getContactDetailsById(userId);
        Comment comment = commentService.createComment(commentDTO, postId, createdBy, parentCommentId);
        return ResponseEntity.ok(ApiResponse.success("Comment created successfully", comment));
    }

    @Operation(summary = "Update a comment")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Comment>> updateComment(
            @Parameter(description = "Comment ID") @PathVariable Long id,
            @Parameter(description = "Updated comment details") @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(ApiResponse.success("Comment updated successfully", comment));
    }

    @Operation(summary = "Delete a comment")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@Parameter(description = "Comment ID") @PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(ApiResponse.success("Comment deleted successfully", null));
    }
} 