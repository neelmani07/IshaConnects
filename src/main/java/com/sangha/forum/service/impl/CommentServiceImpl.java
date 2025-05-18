package com.sangha.forum.service.impl;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.config.ReputationConfig;
import com.sangha.forum.dto.CommentDTO;
import com.sangha.forum.entity.Comment;
import com.sangha.forum.entity.Post;
import com.sangha.common.exception.ResourceNotFoundException;
import com.sangha.forum.repository.CommentRepository;
import com.sangha.forum.repository.PostRepository;
import com.sangha.forum.service.CommentService;
import com.sangha.forum.service.MentionService;
import com.sangha.forum.service.NotificationService;
import com.sangha.forum.service.ReputationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ReputationService reputationService;
    private final MentionService mentionService;
    private final NotificationService notificationService;

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

        Comment savedComment = commentRepository.save(comment);

        // Process mentions in the comment content
        mentionService.processMentions(comment.getContent(), null, savedComment);

        // Award points for creating a comment
        reputationService.addPoints(createdBy, ReputationConfig.COMMENT_CREATION_POINTS, "COMMENT_CREATION", savedComment.getId());

        // Send notification to post owner
        notificationService.sendCommentNotification(post.getUser(), createdBy, postId, savedComment.getId());

        return savedComment;
    }

    @Override
    @Transactional
    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = getCommentById(id);
        comment.setContent(commentDTO.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        // Process mentions in the updated content
        mentionService.processMentions(commentDTO.getContent(), null, comment);

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", "id", id);
        }
        Comment comment = getCommentById(id);
        // Deduct points for comment deletion
        reputationService.removePoints(comment.getCreatedBy(), ReputationConfig.COMMENT_DELETION_POINTS, "COMMENT_DELETION", id);
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void upvoteComment(Long commentId, ContactDetails user) {
        Comment comment = getCommentById(commentId);
        comment.setVotes(comment.getVotes() + 1);
        commentRepository.save(comment);

        // Award points to comment author for upvote
        reputationService.addPoints(comment.getCreatedBy(), ReputationConfig.COMMENT_UPVOTE_POINTS, "COMMENT_UPVOTE", commentId);
    }

    @Override
    @Transactional
    public void downvoteComment(Long commentId, ContactDetails user) {
        Comment comment = getCommentById(commentId);
        comment.setVotes(comment.getVotes() - 1);
        commentRepository.save(comment);

        // Deduct points from comment author for downvote
        reputationService.removePoints(comment.getCreatedBy(), ReputationConfig.COMMENT_DOWNVOTE_POINTS, "COMMENT_DOWNVOTE", commentId);
    }

    @Override
    @Transactional
    public Comment markAsAccepted(Long commentId, ContactDetails user) {
        Comment comment = getCommentById(commentId);
        Post post = comment.getPost();

        // Only post author can mark answers as accepted
        if (!post.getUser().equals(user)) {
            throw new IllegalStateException("Only post author can mark answers as accepted");
        }

        // Unmark any previously accepted answer
        List<Comment> acceptedComments = commentRepository.findByPostIdAndIsAcceptedTrue(post.getId());
        for (Comment acceptedComment : acceptedComments) {
            acceptedComment.setAccepted(false);
            commentRepository.save(acceptedComment);
        }

        // Mark new answer as accepted
        comment.setAccepted(true);
        Comment savedComment = commentRepository.save(comment);

        // Award points for accepted answer
        reputationService.addPoints(comment.getCreatedBy(), ReputationConfig.ANSWER_ACCEPTED_POINTS, "ANSWER_ACCEPTED", commentId);

        // Send notification to the comment author
        notificationService.sendAcceptedAnswerNotification(comment.getCreatedBy(), user, post.getId(), commentId);

        return savedComment;
    }

    @Override
    @Transactional
    public void unmarkAsAccepted(Long commentId, ContactDetails user) {
        Comment comment = getCommentById(commentId);
        Post post = comment.getPost();

        // Only post author can unmark accepted answers
        if (!post.getUser().equals(user)) {
            throw new IllegalStateException("Only post author can unmark accepted answers");
        }

        comment.setAccepted(false);
        commentRepository.save(comment);

        // Deduct points for unmarking accepted answer
        reputationService.removePoints(comment.getCreatedBy(), ReputationConfig.ANSWER_UNMARKED_POINTS, "ANSWER_UNMARKED", commentId);
    }
} 