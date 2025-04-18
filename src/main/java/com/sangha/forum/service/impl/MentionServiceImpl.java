package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.repository.ContactDetailsRepository;
import com.sangha.forum.entity.Comment;
import com.sangha.forum.entity.Mention;
import com.sangha.forum.entity.Post;
import com.sangha.forum.repository.MentionRepository;
import com.sangha.forum.service.MentionService;
import com.sangha.forum.util.MentionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentionServiceImpl implements MentionService {

    @Autowired
    private MentionRepository mentionRepository;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Transactional
    @Override
    public void processMentions(String content, Post post, Comment comment) {
        List<String> usernames = MentionParser.extractMentions(content);
        List<ContactDetails> users = usernames.stream()
            .map(username -> contactDetailsRepository.findByEmail(username))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

        for (ContactDetails user : users) {
            Mention mention = new Mention();
            mention.setMentionedUser(user);
            if (post != null) {
                mention.setPost(post);
            }
            if (comment != null) {
                mention.setComment(comment);
            }
            mention.setRead(false);
            mentionRepository.save(mention);
        }
    }

    @Override
    public Page<Mention> getUnreadMentions(ContactDetails user, Pageable pageable) {
        return mentionRepository.findByMentionedUserAndIsReadFalse(user, pageable);
    }

    @Override
    @Transactional
    public void markAsRead(Long mentionId, ContactDetails user) {
        Mention mention = mentionRepository.findById(mentionId)
            .orElseThrow(() -> new IllegalArgumentException("Mention not found"));
        
        if (!mention.getMentionedUser().equals(user)) {
            throw new IllegalArgumentException("User not authorized to mark this mention as read");
        }
        
        mention.setRead(true);
        mentionRepository.save(mention);
    }

    @Override
    @Transactional
    public void markAllAsRead(ContactDetails user) {
        List<Mention> unreadMentions = (List<Mention>) mentionRepository.findByMentionedUserAndIsReadFalse(user, null);
        for (Mention mention : unreadMentions) {
            mention.setRead(true);
            mentionRepository.save(mention);
        }
    }

    @Override
    public List<Mention> getMentionsForPost(Long postId) {
        return mentionRepository.findByPostId(postId);
    }

    @Override
    public List<Mention> getMentionsForComment(Long commentId) {
        return mentionRepository.findByCommentId(commentId);
    }
} 