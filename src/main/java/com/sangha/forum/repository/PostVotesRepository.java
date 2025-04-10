package com.sangha.forum.repository;

import com.sangha.forum.entity.PostVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Long> {
    List<PostVotes> findByPostId(Long postId);
    List<PostVotes> findByVotedById(Long userId);
    Optional<PostVotes> findByPostIdAndVotedById(Long postId, Long userId);
} 