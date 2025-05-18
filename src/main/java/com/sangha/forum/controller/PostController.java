package com.sangha.forum.controller;

import com.sangha.common.entity.ContactDetails;
import com.sangha.connect.entity.Point;
import com.sangha.common.dto.ApiResponse;
import com.sangha.forum.dto.PostRequest;
import com.sangha.forum.entity.Post;
import com.sangha.forum.service.PostService;
import com.sangha.connect.service.PointService;
import com.sangha.common.service.ContactDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post", description = "Post management APIs")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PointService pointService;
    private final ContactDetailsService contactDetailsService;

    @Operation(summary = "Get paginated posts with advanced filtering")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<Post>>> getPosts(
            @Parameter(description = "Keyword to search in posts") @RequestParam(required = false) String keyword,
            @Parameter(description = "State to filter posts") @RequestParam(required = false) String state,
            @Parameter(description = "City to filter posts") @RequestParam(required = false) String city,
            @Parameter(description = "Start date for filtering") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date for filtering") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Field to sort by (newest, oldest, votes, comments)") @RequestParam(required = false) String sortBy,
            @Parameter(description = "Minimum number of votes") @RequestParam(required = false) Integer minVotes,
            @Parameter(description = "User ID to filter posts") @RequestParam(required = false) Long userId,
            Pageable pageable) {
        Page<Post> posts = postService.getPosts(keyword, state, city, startDate, endDate, sortBy, minVotes, userId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Posts retrieved successfully", posts));
    }

    @Operation(summary = "Get all posts")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Post>>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.success("All posts retrieved successfully", posts));
    }

    @Operation(summary = "Get post by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> getPostById(@Parameter(description = "Post ID") @PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(ApiResponse.success("Post retrieved successfully", post));
    }

    @Operation(summary = "Create a new post")
    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(
            @Parameter(description = "Post details") @RequestBody PostRequest postRequest,
            @Parameter(description = "Point ID") @RequestParam Long pointId,
            @Parameter(description = "User ID") @RequestParam Long userId) {
        Point point = pointService.getPointById(pointId);
        ContactDetails createdBy = contactDetailsService.getContactDetailsById(userId);
        Post post = postService.createPost(postRequest, point, createdBy);
        return ResponseEntity.ok(ApiResponse.success("Post created successfully", post));
    }

    @Operation(summary = "Update a post")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> updatePost(
            @Parameter(description = "Post ID") @PathVariable Long id,
            @Parameter(description = "Updated post details") @RequestBody PostRequest postRequest) {
        Post post = postService.updatePost(id, postRequest);
        return ResponseEntity.ok(ApiResponse.success("Post updated successfully", post));
    }

    @Operation(summary = "Delete a post")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@Parameter(description = "Post ID") @PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success("Post deleted successfully", null));
    }

    @Operation(summary = "Search posts by keyword")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Post>>> searchPosts(
            @Parameter(description = "Search keyword") @RequestParam String keyword) {
        List<Post> posts = postService.searchPostsByKeyword(keyword);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", posts));
    }

    @Operation(summary = "Upvote a post")
    @PostMapping("/{postId}/upvote")
    public ResponseEntity<ApiResponse<Post>> upvotePost(
            @Parameter(description = "Post ID") @PathVariable Long postId,
            @Parameter(description = "User ID") @RequestParam Long userId) {
        ContactDetails voter = contactDetailsService.getContactDetailsById(userId);
        Post post = postService.upvotePost(postId, voter);
        return ResponseEntity.ok(ApiResponse.success("Post upvoted successfully", post));
    }

    @Operation(summary = "Downvote a post")
    @PostMapping("/{postId}/downvote")
    public ResponseEntity<ApiResponse<Post>> downvotePost(
            @Parameter(description = "Post ID") @PathVariable Long postId,
            @Parameter(description = "User ID") @RequestParam Long userId) {
        ContactDetails voter = contactDetailsService.getContactDetailsById(userId);
        Post post = postService.downvotePost(postId, voter);
        return ResponseEntity.ok(ApiResponse.success("Post downvoted successfully", post));
    }

    @PostMapping("/preview")
    @Operation(summary = "Preview post content")
    public ResponseEntity<ApiResponse<String>> previewPost(@RequestBody PostRequest request) {
        String preview = postService.generatePreview(request.getContentHtml());
        return ResponseEntity.ok(ApiResponse.success("Preview generated successfully", preview));
    }
}
