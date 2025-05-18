package com.sangha.forum.repository;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.entity.Category;
import com.sangha.forum.entity.UserSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    boolean existsByUserAndCategory(ContactDetails user, Category category);
    Optional<UserSubscription> findByUserAndCategory(ContactDetails user, Category category);
    Page<UserSubscription> findByUser(ContactDetails user, Pageable pageable);
    List<UserSubscription> findByCategory(Category category);
} 