package com.sangha.forum.controller;

import com.sangha.forum.dto.ApiResponse;
import com.sangha.forum.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Tag(name = "Images", description = "Image upload and management APIs")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "Upload an image")
    public ResponseEntity<ApiResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = imageService.uploadImage(file);
        return ResponseEntity.ok(ApiResponse.success("Image uploaded successfully", imageUrl));
    }

    @DeleteMapping
    @Operation(summary = "Delete an image")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@RequestParam String imageUrl) {
        imageService.deleteImage(imageUrl);
        return ResponseEntity.ok(ApiResponse.success("Image deleted successfully", null));
    }
} 