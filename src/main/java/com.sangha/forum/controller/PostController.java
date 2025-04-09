package com.sangha.forum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String filterBy) {
        return ResponseEntity.ok(postService.getPosts(keyword, sortBy, filterBy));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(postService.addComment(postId, commentDTO));
    }

    @PostMapping("/{postId}/upvote")
    public ResponseEntity<Void> upvotePost(@PathVariable Long postId) {
        postService.upvotePost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/downvote")
    public ResponseEntity<Void> downvotePost(@PathVariable Long postId) {
        postService.downvotePost(postId);
        return ResponseEntity.ok().build();
    }
}
