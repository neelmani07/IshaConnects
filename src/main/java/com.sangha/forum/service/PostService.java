package com.sangha.connect.service;

import com.sangha.connect.entity.Post;
import com.sangha.connect.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    // Create a new post
    Post createPost(Post post);

    // Fetch posts with filtering, sorting, and pagination
    Page<Post> getPosts(String keyword, String state, String city, String sortBy, Pageable pageable);

    List<Post> getAllPosts();

    // Fetch a single post by ID
    Post getPostById(Long postId);

    // Add a comment to a post
    Comment addComment(Long postId, Comment comment);

    // Upvote a post
    void upvotePost(Long postId);

    // Downvote a post
    void downvotePost(Long postId);

    List<Post> searchPostsByKeyword(String keyword);

    void deletePost(Long id);
}
