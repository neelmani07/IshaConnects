package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.CommentDTO;
import com.sangha.forum.entity.Comment;
import com.sangha.forum.entity.Post;
import com.sangha.forum.exception.ResourceNotFoundException;
import com.sangha.forum.repository.CommentRepository;
import com.sangha.forum.repository.PostRepository;
import com.sangha.forum.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post", "id", postId);
        }
        return commentRepository.findByPostId(postId);
    }

    @Override
    @Transactional
    public Comment createComment(CommentDTO commentDTO, Long postId, ContactDetails createdBy, Long parentCommentId) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedBy(createdBy);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

        if (parentCommentId != null) {
            Comment parentComment = getCommentById(parentCommentId);
            comment.setParentComment(parentComment);
        }

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = getCommentById(id);
        comment.setContent(commentDTO.getContent());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", "id", id);
        }
        commentRepository.deleteById(id);
    }
} 