package com.sangha.forum.repository;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.Category;
import com.sangha.forum.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findByUserAndCategory(ContactDetails user, Category category);
    List<UserSubscription> findByUser(ContactDetails user);
    List<UserSubscription> findByCategory(Category category);
    boolean existsByUserAndCategory(ContactDetails user, Category category);
} 