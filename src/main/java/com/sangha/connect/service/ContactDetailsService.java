package com.sangha.connect.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactDetailsService {
    List<ContactDetails> getAllContactDetails();
    ContactDetails getContactDetailsById(Long id) throws ResourceNotFoundException;
    
    @Transactional
    ContactDetails createContactDetails(ContactDetails contactDetails);
    
    @Transactional
    ContactDetails updateContactDetails(Long id, ContactDetails contactDetails) throws ResourceNotFoundException;
    
    @Transactional
    void deleteContactDetails(Long id) throws ResourceNotFoundException;
} 