package com.sangha.forum.controller;

import com.sangha.forum.dto.UserSuggestionDTO;
import com.sangha.forum.service.UserSuggestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/suggestions")
@Tag(name = "User Suggestions", description = "APIs for user suggestions in mentions")
public class UserSuggestionController {

    @Autowired
    private UserSuggestionService userSuggestionService;

    @GetMapping
    @Operation(summary = "Get user suggestions for mentions")
    public ResponseEntity<List<UserSuggestionDTO>> getSuggestions(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(userSuggestionService.getSuggestions(query, limit));
    }
} 