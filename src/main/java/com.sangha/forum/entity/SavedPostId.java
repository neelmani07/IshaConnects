package com.sangha.forum.entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class SavedPostId {
    private Long post;
    private Long user;
}