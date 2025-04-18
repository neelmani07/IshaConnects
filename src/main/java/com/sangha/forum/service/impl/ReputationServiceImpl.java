package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.ReputationHistory;
import com.sangha.forum.entity.UserReputation;
import com.sangha.forum.repository.ReputationHistoryRepository;
import com.sangha.forum.repository.UserReputationRepository;
import com.sangha.forum.service.ReputationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReputationServiceImpl implements ReputationService {

    private final UserReputationRepository userReputationRepository;
    private final ReputationHistoryRepository reputationHistoryRepository;

    @Autowired
    public ReputationServiceImpl(
            UserReputationRepository userReputationRepository,
            ReputationHistoryRepository reputationHistoryRepository) {
        this.userReputationRepository = userReputationRepository;
        this.reputationHistoryRepository = reputationHistoryRepository;
    }

    @Override
    public UserReputation getUserReputation(ContactDetails user) {
        return userReputationRepository.findByUser(user)
                .orElseGet(() -> {
                    UserReputation newReputation = new UserReputation();
                    newReputation.setUser(user);
                    newReputation.setTotalPoints(0);
                    newReputation.setCurrentLevel(1);
                    return userReputationRepository.save(newReputation);
                });
    }

    @Override
    @Transactional
    public void addPoints(ContactDetails user, int points, String sourceType, Long sourceId) {
        UserReputation reputation = getUserReputation(user);
        reputation.setTotalPoints(reputation.getTotalPoints() + points);
        reputation.setCurrentLevel(calculateLevel(reputation.getTotalPoints()));
        userReputationRepository.save(reputation);

        ReputationHistory history = new ReputationHistory();
        history.setUser(user);
        history.setPointsChange(points);
        history.setSourceType(sourceType);
        history.setSourceId(sourceId);
        reputationHistoryRepository.save(history);
    }

    @Override
    @Transactional
    public void removePoints(ContactDetails user, int points, String sourceType, Long sourceId) {
        UserReputation reputation = getUserReputation(user);
        int newTotal = Math.max(0, reputation.getTotalPoints() - points);
        reputation.setTotalPoints(newTotal);
        reputation.setCurrentLevel(calculateLevel(newTotal));
        userReputationRepository.save(reputation);

        ReputationHistory history = new ReputationHistory();
        history.setUser(user);
        history.setPointsChange(-points);
        history.setSourceType(sourceType);
        history.setSourceId(sourceId);
        reputationHistoryRepository.save(history);
    }

    @Override
    public Page<ReputationHistory> getReputationHistory(ContactDetails user, Pageable pageable) {
        return reputationHistoryRepository.findByUser(user, pageable);
    }

    @Override
    public int calculateLevel(int totalPoints) {
        // Simple level calculation: level = floor(sqrt(totalPoints / 100)) + 1
        return (int) Math.floor(Math.sqrt(totalPoints / 100.0)) + 1;
    }
}