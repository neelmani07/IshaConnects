package com.sangha.forum.controller;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.Category;
import com.sangha.forum.entity.UserSubscription;
import com.sangha.forum.service.UserSubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "User Subscriptions", description = "User subscription management APIs")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @Autowired
    public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @PostMapping("/categories/{categoryId}")
    @Operation(summary = "Subscribe to a category")
    public ResponseEntity<UserSubscription> subscribeToCategory(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal ContactDetails user) {
        Category category = new Category();
        category.setId(categoryId);
        return ResponseEntity.ok(userSubscriptionService.subscribe(user, category));
    }

    @DeleteMapping("/categories/{categoryId}")
    @Operation(summary = "Unsubscribe from a category")
    public ResponseEntity<Void> unsubscribeFromCategory(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal ContactDetails user) {
        Category category = new Category();
        category.setId(categoryId);
        userSubscriptionService.unsubscribe(user, category);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get user's subscriptions")
    public ResponseEntity<Page<UserSubscription>> getUserSubscriptions(
            @AuthenticationPrincipal ContactDetails user,
            Pageable pageable) {
        return ResponseEntity.ok(userSubscriptionService.getUserSubscriptions(user, pageable));
    }

    @GetMapping("/categories/{categoryId}")
    @Operation(summary = "Check if user is subscribed to a category")
    public ResponseEntity<Boolean> isSubscribedToCategory(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal ContactDetails user) {
        Category category = new Category();
        category.setId(categoryId);
        return ResponseEntity.ok(userSubscriptionService.isSubscribed(user, category));
    }
} 