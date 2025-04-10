package com.sangha.connect.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.ContactDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactDetailsService {

    private final ContactDetailsRepository contactDetailsRepository;

    public List<ContactDetails> getAllContactDetails() {
        return contactDetailsRepository.findAll();
    }

    public ContactDetails getContactDetailsById(Long id) {
        return contactDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactDetails", "id", id));
    }

    @Transactional
    public ContactDetails createContactDetails(ContactDetails contactDetails) {
        return contactDetailsRepository.save(contactDetails);
    }

    @Transactional
    public ContactDetails updateContactDetails(Long id, ContactDetails contactDetails) {
        if (!contactDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("ContactDetails", "id", id);
        }
        contactDetails.setId(id);
        return contactDetailsRepository.save(contactDetails);
    }

    @Transactional
    public void deleteContactDetails(Long id) {
        if (!contactDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("ContactDetails", "id", id);
        }
        contactDetailsRepository.deleteById(id);
    }
} 