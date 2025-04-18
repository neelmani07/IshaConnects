package com.sangha.forum.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    void sendMentionNotification(ContactDetails mentionedUser, ContactDetails mentionedBy, 
                               Long postId, Long commentId, String content);
    Page<NotificationDTO> getUserNotifications(ContactDetails user, Pageable pageable);
    void markNotificationAsRead(Long notificationId, ContactDetails user);
    void markAllNotificationsAsRead(ContactDetails user);
} 