package com.sangha.forum.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.entity.Point;
import com.sangha.forum.dto.PostRequest;
import com.sangha.forum.entity.Post;
import com.sangha.forum.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    // Create a new post
    Post createPost(PostRequest request, Point point, ContactDetails createdBy);

    // Fetch posts with filtering, sorting, and pagination
    Page<Post> getPosts(String keyword, String state, String city, String sortBy, Pageable pageable);

    List<Post> getAllPosts();

    // Fetch a single post by ID
    Post getPostById(Long id);

    // Add a comment to a post
    Comment addComment(Long postId, Comment comment);

    // Upvote a post
    Post upvotePost(Long id, ContactDetails voter);

    // Downvote a post
    Post downvotePost(Long id, ContactDetails voter);

    List<Post> searchPostsByKeyword(String keyword);

    void deletePost(Long id);

    Post updatePost(Long id, PostRequest request);
}
