package com.sangha.forum.service;

import com.sangha.connect.entity.ContactDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ContactDetailsService extends UserDetailsService {
    ContactDetails save(ContactDetails user);
    ContactDetails findByEmail(String email);
} 