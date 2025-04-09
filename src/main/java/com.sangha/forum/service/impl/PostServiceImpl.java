package com.sangha.connect.service;

import com.sangha.connect.entity.Post;
import com.sangha.connect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements com.sangha.connect.service.PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> searchPostsByKeyword(String keyword) {
        return postRepository.findByContentContainingIgnoreCase(keyword);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
