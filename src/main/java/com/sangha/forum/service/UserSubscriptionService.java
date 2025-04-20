package com.sangha.forum.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.Category;
import com.sangha.forum.entity.UserSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserSubscriptionService {
    UserSubscription subscribe(ContactDetails user, Category category);
    void unsubscribe(ContactDetails user, Category category);
    Page<UserSubscription> getUserSubscriptions(ContactDetails user, Pageable pageable);
    boolean isSubscribed(ContactDetails user, Category category);
    void notifySubscribers(Category category, String message);
} 