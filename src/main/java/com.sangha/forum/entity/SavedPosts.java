package com.sangha.forum.entity;

import com.sangha.connect.entity.ContactDetails;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "saved_posts")
@IdClass(SavedPostId.class)
public class SavedPosts {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ContactDetails user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}