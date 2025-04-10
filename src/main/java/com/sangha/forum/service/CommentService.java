package com.sangha.forum.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.CommentDTO;
import com.sangha.forum.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    Comment getCommentById(Long id);
    List<Comment> getCommentsByPostId(Long postId);
    Comment createComment(CommentDTO commentDTO, Long postId, ContactDetails createdBy, Long parentCommentId);
    Comment updateComment(Long id, CommentDTO commentDTO);
    void deleteComment(Long id);
} 