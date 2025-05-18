package com.sangha.forum.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "contact_details")
public class ContactDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private int failedAttempt = 0;
    private boolean accountNonLocked = true;
    private LocalDateTime lockTime;
    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    @ElementCollection
    @CollectionTable(name = "password_history", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "password")
    private Set<String> passwordHistory = new HashSet<>();

    // Additional fields for user profile
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String profilePicture;
    private String bio;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 