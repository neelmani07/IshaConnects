package com.sangha.forum.repository;

import com.sangha.forum.entity.PostTags;
import com.sangha.forum.entity.PostTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagsRepository extends JpaRepository<PostTags, PostTagId> {
    List<PostTags> findByPostId(Long postId);
    List<PostTags> findByTagId(Long tagId);
    boolean existsByPostIdAndTagId(Long postId, Long tagId);
} 