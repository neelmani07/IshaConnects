package com.sangha.forum.controller;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.dto.NotificationDTO;
import com.sangha.forum.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Notification management APIs")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @Operation(summary = "Get user notifications")
    public ResponseEntity<Page<NotificationDTO>> getUserNotifications(
            @AuthenticationPrincipal ContactDetails user,
            Pageable pageable) {
        return ResponseEntity.ok(notificationService.getUserNotifications(user, pageable));
    }

    @PostMapping("/{id}/read")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal ContactDetails user) {
        notificationService.markNotificationAsRead(id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/read-all")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<Void> markAllAsRead(@AuthenticationPrincipal ContactDetails user) {
        notificationService.markAllNotificationsAsRead(user);
        return ResponseEntity.ok().build();
    }

    @SubscribeMapping("/user/queue/notifications")
    public void subscribeToNotifications(@AuthenticationPrincipal ContactDetails user) {
        // This method is called when a user subscribes to their notification queue
        // No need to return anything as notifications will be pushed via WebSocket
    }
} 