package com.sangha.forum.repository;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.Mention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentionRepository extends JpaRepository<Mention, Long> {
    Page<Mention> findByMentionedUserAndIsReadFalse(ContactDetails user, Pageable pageable);
    List<Mention> findByPostId(Long postId);
    List<Mention> findByCommentId(Long commentId);
} 