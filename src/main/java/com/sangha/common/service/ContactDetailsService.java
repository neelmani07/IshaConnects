package com.sangha.common.service;

import com.sangha.common.entity.ContactDetails;
import com.sangha.common.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactDetailsService extends UserDetailsService {
    // Authentication methods
    ContactDetails save(ContactDetails user);
    ContactDetails findByEmail(String email);
    
    // CRUD operations
    List<ContactDetails> getAllContactDetails();
    ContactDetails getContactDetailsById(Long id) throws ResourceNotFoundException;
    
    @Transactional
    ContactDetails createContactDetails(ContactDetails contactDetails);
    
    @Transactional
    ContactDetails updateContactDetails(Long id, ContactDetails contactDetails) throws ResourceNotFoundException;
    
    @Transactional
    void deleteContactDetails(Long id) throws ResourceNotFoundException;
} 