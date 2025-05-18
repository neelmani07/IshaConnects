package com.sangha.forum.repository;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.entity.ReputationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReputationHistoryRepository extends JpaRepository<ReputationHistory, Long> {
    Page<ReputationHistory> findByUser(ContactDetails user, Pageable pageable);
} 