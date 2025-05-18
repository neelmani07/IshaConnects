package com.sangha.forum.service;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.dto.CommentDTO;
import com.sangha.forum.entity.Comment;
import com.sangha.forum.entity.Post;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();
    Comment getCommentById(Long id);
    List<Comment> getCommentsByPostId(Long postId);
    Comment createComment(CommentDTO commentDTO, Long postId, ContactDetails createdBy, Long parentCommentId);
    Comment updateComment(Long id, CommentDTO commentDTO);
    void deleteComment(Long id);
    void upvoteComment(Long commentId, ContactDetails user);
    void downvoteComment(Long commentId, ContactDetails user);
    Comment markAsAccepted(Long commentId, ContactDetails user);
    void unmarkAsAccepted(Long commentId, ContactDetails user);
} 