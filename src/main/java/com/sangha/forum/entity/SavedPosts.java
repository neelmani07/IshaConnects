package com.sangha.forum.entity;

import com.sangha.connect.entity.ContactDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saved_posts")
@IdClass(SavedPostId.class)
public class SavedPosts {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}