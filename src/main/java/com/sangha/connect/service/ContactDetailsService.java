package com.sangha.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.repository.ContactDetailsRepository;

@Service
public class ContactDetailsService {

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    // Create or update contact details
    public ContactDetails saveOrUpdateContactDetails(ContactDetails contactDetails) {
        return contactDetailsRepository.save(contactDetails);
    }

    // Retrieve all contact details
    public List<ContactDetails> getAllContactDetails() {
        return contactDetailsRepository.findAll();
    }

    // Retrieve contact details by ID
    public Optional<ContactDetails> getContactDetailsById(Long id) {
        return contactDetailsRepository.findById(id);
    }

    // Delete contact details by ID
    public void deleteContactDetailsById(Long id) {
        contactDetailsRepository.deleteById(id);
    }
}
