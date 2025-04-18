package com.sangha.forum.repository;

import com.sangha.forum.entity.PostModeration;
import com.sangha.forum.entity.ModerationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostModerationRepository extends JpaRepository<PostModeration, Long> {
    Optional<PostModeration> findFirstByPostIdOrderByCreatedAtDesc(Long postId);
    Page<PostModeration> findByStatus(ModerationStatus status, Pageable pageable);
    List<PostModeration> findByPostId(Long postId);
    Page<PostModeration> findByModeratorId(Long moderatorId, Pageable pageable);
} 