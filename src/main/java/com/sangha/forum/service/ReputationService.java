package com.sangha.forum.service;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.entity.ReputationHistory;
import com.sangha.forum.entity.UserReputation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReputationService {
    UserReputation getUserReputation(ContactDetails user);
    void addPoints(ContactDetails user, int points, String reason, Long entityId);
    void removePoints(ContactDetails user, int points, String reason, Long entityId);
    int getPoints(ContactDetails user);
    Page<ReputationHistory> getReputationHistory(ContactDetails user, Pageable pageable);
    int calculateLevel(int totalPoints);
} 