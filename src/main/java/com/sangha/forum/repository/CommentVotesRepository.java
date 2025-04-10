package com.sangha.forum.repository;

import com.sangha.forum.entity.CommentVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentVotesRepository extends JpaRepository<CommentVotes, Long> {
    List<CommentVotes> findByCommentId(Long commentId);
    List<CommentVotes> findByVotedById(Long userId);
    CommentVotes findByCommentIdAndVotedById(Long commentId, Long userId);
} 