package com.sangha.forum.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile file);
    void deleteImage(String imageUrl);
} 