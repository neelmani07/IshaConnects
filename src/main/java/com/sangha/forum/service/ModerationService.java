package com.sangha.forum.service;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.dto.ModerationRequestDTO;
import com.sangha.forum.entity.PostModeration;
import com.sangha.forum.entity.ModerationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModerationService {
    @Transactional
    PostModeration moderatePost(ModerationRequestDTO request, ContactDetails moderator);
    
    List<PostModeration> getModerationHistory(Long postId);
    
    Page<PostModeration> getPendingModerations(Pageable pageable);
    
    Page<PostModeration> getModeratedByUser(Long moderatorId, Pageable pageable);
} 