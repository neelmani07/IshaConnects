package com.sangha.forum.service;

import com.sangha.forum.dto.UserSuggestionDTO;
import java.util.List;

public interface UserSuggestionService {
    List<UserSuggestionDTO> getSuggestions(String query, int limit);
} 