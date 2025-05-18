package com.sangha.forum.service;

import com.sangha.common.entity.ContactDetails;
import com.sangha.connect.entity.Point;
import com.sangha.forum.dto.PostRequest;
import com.sangha.forum.entity.Post;
import com.sangha.forum.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    // Create a new post
    Post createPost(PostRequest request, Point point, ContactDetails createdBy);

    // Enhanced post fetching with all filters
    Page<Post> getPosts(
            String keyword,
            String state,
            String city,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String sortBy,
            Integer minVotes,
            Long userId,
            Pageable pageable);

    // Get all posts (basic)
    List<Post> getAllPosts();

    // Get post by ID
    Post getPostById(Long id);

    // Add a comment to a post
    Comment addComment(Long postId, Comment comment);

    // Upvote a post
    Post upvotePost(Long id, ContactDetails voter);

    // Downvote a post
    Post downvotePost(Long id, ContactDetails voter);

    // Search posts by keyword
    List<Post> searchPostsByKeyword(String keyword);

    // Delete a post
    void deletePost(Long id);

    // Update a post
    Post updatePost(Long id, PostRequest request);

    // Generate preview for post content
    String generatePreview(String htmlContent);
}
