package com.sangha.forum.repository;

import com.sangha.forum.entity.SavedPosts;
import com.sangha.forum.entity.SavedPostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedPostsRepository extends JpaRepository<SavedPosts, SavedPostId> {
    List<SavedPosts> findByPostId(Long postId);
    List<SavedPosts> findByUserId(Long userId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
} 