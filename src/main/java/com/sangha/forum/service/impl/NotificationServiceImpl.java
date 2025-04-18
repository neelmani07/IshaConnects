package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.NotificationDTO;
import com.sangha.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    @Transactional
    public void sendMentionNotification(ContactDetails mentionedUser, ContactDetails mentionedBy, Long postId, Long commentId, String content) {
        NotificationDTO notification = new NotificationDTO();
        notification.setType("MENTION");
        notification.setMessage(mentionedBy.getName() + " mentioned you in " + (commentId != null ? "a comment" : "a post"));
        notification.setPostId(postId);
        notification.setCommentId(commentId);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        messagingTemplate.convertAndSendToUser(mentionedUser.getEmail(), "/topic/notifications", notification);
    }

    @Override
    @Transactional
    public void sendUpvoteNotification(ContactDetails postOwner, ContactDetails user, Long postId) {
        NotificationDTO notification = new NotificationDTO();
        notification.setType("UPVOTE");
        notification.setMessage(user.getName() + " upvoted your post.");
        notification.setPostId(postId);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        messagingTemplate.convertAndSendToUser(postOwner.getEmail(), "/topic/notifications", notification);
    }

    @Override
    @Transactional
    public void sendAcceptedAnswerNotification(ContactDetails postOwner, ContactDetails user, Long postId, Long commentId) {
        NotificationDTO notification = new NotificationDTO();
        notification.setType("ACCEPTED_ANSWER");
        notification.setMessage(user.getName() + " accepted your answer.");
        notification.setPostId(postId);
        notification.setCommentId(commentId);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        messagingTemplate.convertAndSendToUser(postOwner.getEmail(), "/topic/notifications", notification);
    }

    @Override
    @Transactional
    public void sendCommentNotification(ContactDetails postOwner, ContactDetails user, Long postId, Long commentId) {
        NotificationDTO notification = new NotificationDTO();
        notification.setType("COMMENT");
        notification.setMessage(user.getName() + " commented on your post.");
        notification.setPostId(postId);
        notification.setCommentId(commentId);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        messagingTemplate.convertAndSendToUser(postOwner.getEmail(), "/topic/notifications", notification);
    }

    @Override
    public Page<NotificationDTO> getUserNotifications(ContactDetails user, Pageable pageable) {
        // TODO: Implement if we want to store notifications in database
        return null;
    }

    @Override
    public void markNotificationAsRead(Long notificationId, ContactDetails user) {
        // TODO: Implement if we want to store notifications in database
    }

    @Override
    public void markAllNotificationsAsRead(ContactDetails user) {
        // TODO: Implement if we want to store notifications in database
    }
} 