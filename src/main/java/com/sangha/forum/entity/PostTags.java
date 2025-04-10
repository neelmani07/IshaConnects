package com.sangha.forum.entity;

import com.sangha.forum.entity.PostTagId;
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
@Table(name = "post_tags")
@IdClass(PostTagId.class)
public class PostTags {

    @Id
    @Column(name = "post_id")
    private Long postId;

    @Id
    @Column(name = "tag_id")
    private Long tagId;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}