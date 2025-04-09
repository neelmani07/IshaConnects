package com.sangha.forum.entity;

import com.sangha.forum.entity.PostTagId;
import jakarta.persistence.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_tags")
@IdClass(PostTagId.class)
public class PostTags {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}