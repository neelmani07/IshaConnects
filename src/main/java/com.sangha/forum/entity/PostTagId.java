package com.sangha.forum.entity;

import jakarta.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class PostTagId {
    private Long post;
    private Long tag;
}