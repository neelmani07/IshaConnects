package com.sangha.forum.service.impl;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.entity.Category;
import com.sangha.forum.entity.UserSubscription;
import com.sangha.forum.repository.UserSubscriptionRepository;
import com.sangha.forum.service.NotificationService;
import com.sangha.forum.service.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final NotificationService notificationService;

    @Autowired
    public UserSubscriptionServiceImpl(
            UserSubscriptionRepository userSubscriptionRepository,
            NotificationService notificationService) {
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public UserSubscription subscribe(ContactDetails user, Category category) {
        if (userSubscriptionRepository.existsByUserAndCategory(user, category)) {
            throw new IllegalStateException("User is already subscribed to this category");
        }

        UserSubscription subscription = new UserSubscription();
        subscription.setUser(user);
        subscription.setCategory(category);
        return userSubscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public void unsubscribe(ContactDetails user, Category category) {
        UserSubscription subscription = userSubscriptionRepository
                .findByUserAndCategory(user, category)
                .orElseThrow(() -> new IllegalStateException("User is not subscribed to this category"));
        
        userSubscriptionRepository.delete(subscription);
    }

    @Override
    public Page<UserSubscription> getUserSubscriptions(ContactDetails user, Pageable pageable) {
        return userSubscriptionRepository.findByUser(user, pageable);
    }

    @Override
    public boolean isSubscribed(ContactDetails user, Category category) {
        return userSubscriptionRepository.existsByUserAndCategory(user, category);
    }

    @Override
    @Transactional
    public void notifySubscribers(Category category, String message) {
        userSubscriptionRepository.findByCategory(category).forEach(subscription -> {
            notificationService.sendCategoryNotification(
                subscription.getUser(),
                category,
                message
            ); 
        });
    }
} 