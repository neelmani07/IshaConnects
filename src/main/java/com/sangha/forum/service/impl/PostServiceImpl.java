package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.entity.Point;
import com.sangha.forum.dto.PostRequest;
import com.sangha.forum.entity.Post;
import com.sangha.forum.entity.Comment;
import com.sangha.forum.entity.PostVotes;
import com.sangha.forum.entity.VoteType;
import com.sangha.forum.exception.ResourceNotFoundException;
import com.sangha.forum.repository.PostRepository;
import com.sangha.forum.repository.PostVotesRepository;
import com.sangha.forum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;

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
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setPoint(point);
        post.setCreatedBy(createdBy);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post updatePost(Long id, PostRequest request) {
        Post post = getPostById(id);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post", "id", id);
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> searchPostsByKeyword(String keyword) {
        return postRepository.findByContentContainingIgnoreCase(keyword);
    }

    @Override
    public Page<Post> getPosts(String keyword, String state, String city, String sortBy, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return postRepository.findByContentContainingIgnoreCase(keyword, pageable);
        }
        return postRepository.findAll(pageable);
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
        
        return postRepository.save(post);
    }
}
