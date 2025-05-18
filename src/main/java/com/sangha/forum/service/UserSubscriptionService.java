package com.sangha.forum.service;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.entity.Category;
import com.sangha.forum.entity.UserSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface UserSubscriptionService {
    @Transactional
    UserSubscription subscribe(ContactDetails user, Category category);
    
    @Transactional
    void unsubscribe(ContactDetails user, Category category);
    
    Page<UserSubscription> getUserSubscriptions(ContactDetails user, Pageable pageable);
    
    boolean isSubscribed(ContactDetails user, Category category);
    
    @Transactional
    void notifySubscribers(Category category, String message);
} 