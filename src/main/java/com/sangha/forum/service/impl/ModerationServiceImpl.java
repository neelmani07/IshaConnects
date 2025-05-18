package com.sangha.forum.service.impl;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.dto.ModerationRequestDTO;
import com.sangha.forum.entity.Post;
import com.sangha.forum.entity.PostModeration;
import com.sangha.forum.entity.ModerationStatus;
import com.sangha.common.exception.ResourceNotFoundException;
import com.sangha.forum.repository.PostModerationRepository;
import com.sangha.forum.repository.PostRepository;
import com.sangha.forum.service.ModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModerationServiceImpl implements ModerationService {

    private final PostModerationRepository postModerationRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostModeration moderatePost(ModerationRequestDTO request, ContactDetails moderator) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", request.getPostId()));

        PostModeration moderation = new PostModeration();
        moderation.setPost(post);
        moderation.setModerator(moderator);
        moderation.setStatus(request.getStatus());
        moderation.setReason(request.getReason());

        return postModerationRepository.save(moderation);
    }

    @Override
    public List<PostModeration> getModerationHistory(Long postId) {
        return postModerationRepository.findByPostId(postId);
    }

    @Override
    public Page<PostModeration> getPendingModerations(Pageable pageable) {
        return postModerationRepository.findByStatus(ModerationStatus.PENDING, pageable);
    }

    @Override
    public Page<PostModeration> getModeratedByUser(Long moderatorId, Pageable pageable) {
        return postModerationRepository.findByModeratorId(moderatorId, pageable);
    }
} 