package com.sangha.forum.service.impl;

import com.sangha.common.entity.ContactDetails;
import com.sangha.connect.entity.Point;
import com.sangha.forum.dto.PostRequest;
import com.sangha.forum.entity.Post;
import com.sangha.forum.entity.Comment;
import com.sangha.forum.entity.PostVotes;
import com.sangha.forum.entity.VoteType;
import com.sangha.common.exception.ResourceNotFoundException;
import com.sangha.forum.repository.PostRepository;
import com.sangha.forum.repository.PostVotesRepository;
import com.sangha.forum.service.NotificationService;
import com.sangha.forum.service.PostService;
import com.sangha.forum.service.ReputationService;
import com.sangha.forum.util.ContentSanitizer;
import com.sangha.forum.config.ReputationConfig;
import com.sangha.forum.service.MentionService;
import com.sangha.forum.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final ContentSanitizer contentSanitizer;
    private final ReputationService reputationService;
    private final MentionService mentionService;
    private final NotificationService notificationService;
    private final UserSubscriptionService userSubscriptionService;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    @Transactional
    public Post createPost(PostRequest request, Point point, ContactDetails createdBy) {
        Post post = new Post();
        post.setTitle(contentSanitizer.sanitizePlainText(request.getTitle()));
        post.setContent(request.getContent());
        post.setContentHtml(contentSanitizer.sanitizeHtml(request.getContentHtml()));
        post.setContentPlain(contentSanitizer.sanitizePlainText(request.getContentPlain()));
        post.setContentPreview(contentSanitizer.generatePreview(request.getContentHtml()));
        post.setEditorVersion(request.getEditorVersion());
        post.setPoint(point);
        post.setCreatedBy(createdBy);
        post.setImageUrl(request.getImageUrl());
        Post savedPost = postRepository.save(post);

        // Process mentions in the post content
        mentionService.processMentions(post.getContent(), savedPost, null);

        // Notify category subscribers
        if (post.getCategory() != null) {
            userSubscriptionService.notifySubscribers(
                post.getCategory(),
                "New post in category " + post.getCategory().getName() + ": " + post.getTitle()
            );
        }

        // Award points for creating a post
        reputationService.addPoints(createdBy, ReputationConfig.POST_CREATION_POINTS, "POST_CREATION", savedPost.getId());

        return savedPost;
    }

    @Override
    @Transactional
    public Post updatePost(Long id, PostRequest request) {
        Post post = getPostById(id);
        post.setTitle(contentSanitizer.sanitizePlainText(request.getTitle()));
        post.setContent(request.getContent());
        post.setContentHtml(contentSanitizer.sanitizeHtml(request.getContentHtml()));
        post.setContentPlain(contentSanitizer.sanitizePlainText(request.getContentPlain()));
        post.setContentPreview(contentSanitizer.generatePreview(request.getContentHtml()));
        post.setEditorVersion(request.getEditorVersion());
        post.setImageUrl(request.getImageUrl());
        post.setUpdatedAt(LocalDateTime.now());

        // Process mentions in the updated content
        mentionService.processMentions(request.getContent(), post, null);

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post", "id", id);
        }
        Post post = getPostById(id);
        // Deduct points for post deletion
        reputationService.removePoints(post.getCreatedBy(), ReputationConfig.POST_DELETION_POINTS, "POST_DELETION", id);
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> searchPostsByKeyword(String keyword) {
        return postRepository.searchByKeyword(keyword, Pageable.unpaged()).getContent();
    }

    @Override
    public Page<Post> getPosts(
            String keyword,
            String state,
            String city,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String sortBy,
            Integer minVotes,
            Long userId,
            Pageable pageable) {

        // If userId is provided, get user's posts
        if (userId != null) {
            return postRepository.findByCreatedById(userId, pageable);
        }

        // If minVotes is provided, get posts with minimum votes
        if (minVotes != null && minVotes > 0) {
            return postRepository.findByMinVotes(minVotes, pageable);
        }

        // If sortBy is provided, get sorted posts
        if (sortBy != null && !sortBy.isEmpty()) {
            return postRepository.findAllSorted(sortBy, pageable);
        }

        // Use combined filters
        return postRepository.searchWithFilters(
                keyword, state, city, startDate, endDate, pageable);
    }

    @Override
    @Transactional
    public Comment addComment(Long postId, Comment comment) {
        Post post = getPostById(postId);
        comment.setPost(post);
        return comment;
    }

    @Override
    @Transactional
    public Post upvotePost(Long id, ContactDetails voter) {
        Post post = getPostById(id);
        Optional<PostVotes> existingVote = postVotesRepository.findByPostIdAndVotedById(id, voter.getId());

        if (existingVote.isPresent()) {
            PostVotes vote = existingVote.get();
            if (vote.getVoteType() == VoteType.DOWN) {
                vote.setVoteType(VoteType.UP);
                postVotesRepository.save(vote);
            }
        } else {
            PostVotes newVote = new PostVotes();
            newVote.setPost(post);
            newVote.setVotedBy(voter);
            newVote.setVoteType(VoteType.UP);
            postVotesRepository.save(newVote);
        }

        // Award points to post author for upvote
        reputationService.addPoints(post.getCreatedBy(), ReputationConfig.POST_UPVOTE_POINTS, "POST_UPVOTE", id);

        // Send notification to post owner
        notificationService.sendUpvoteNotification(post.getUser(), voter, id);

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post downvotePost(Long id, ContactDetails voter) {
        Post post = getPostById(id);
        Optional<PostVotes> existingVote = postVotesRepository.findByPostIdAndVotedById(id, voter.getId());

        if (existingVote.isPresent()) {
            PostVotes vote = existingVote.get();
            if (vote.getVoteType() == VoteType.UP) {
                vote.setVoteType(VoteType.DOWN);
                postVotesRepository.save(vote);
            }
        } else {
            PostVotes newVote = new PostVotes();
            newVote.setPost(post);
            newVote.setVotedBy(voter);
            newVote.setVoteType(VoteType.DOWN);
            postVotesRepository.save(newVote);
        }

        // Deduct points from post author for downvote
        reputationService.removePoints(post.getCreatedBy(), ReputationConfig.POST_DOWNVOTE_POINTS, "POST_DOWNVOTE", id);

        return postRepository.save(post);
    }

    @Override
    public String generatePreview(String htmlContent) {
        return contentSanitizer.generatePreview(htmlContent);
    }
}
