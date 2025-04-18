package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.repository.ContactDetailsRepository;
import com.sangha.forum.dto.UserSuggestionDTO;
import com.sangha.forum.service.UserSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSuggestionServiceImpl implements UserSuggestionService {

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Override
    public List<UserSuggestionDTO> getSuggestions(String query, int limit) {
        List<ContactDetails> users = contactDetailsRepository
            .findByEmailContainingIgnoreCaseOrNameContainingIgnoreCase(query, query);
        
        return users.stream()
            .limit(limit)
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private UserSuggestionDTO convertToDTO(ContactDetails user) {
        UserSuggestionDTO dto = new UserSuggestionDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getEmail());
        dto.setDisplayName(user.getName());
        // TODO: Add avatar URL when implemented
        return dto;
    }
} 