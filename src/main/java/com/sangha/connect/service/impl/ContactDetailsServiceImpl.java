package com.sangha.connect.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.ContactDetailsRepository;
import com.sangha.connect.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final ContactDetailsRepository contactDetailsRepository;

    @Override
    public List<ContactDetails> getAllContactDetails() {
        return contactDetailsRepository.findAll();
    }

    @Override
    public ContactDetails getContactDetailsById(Long id) throws ResourceNotFoundException {
        return contactDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactDetails", "id", id));
    }

    @Override
    @Transactional
    public ContactDetails createContactDetails(ContactDetails contactDetails) {
        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    @Transactional
    public ContactDetails updateContactDetails(Long id, ContactDetails contactDetails) throws ResourceNotFoundException {
        if (!contactDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("ContactDetails", "id", id);
        }
        contactDetails.setId(id);
        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    @Transactional
    public void deleteContactDetails(Long id) throws ResourceNotFoundException {
        if (!contactDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("ContactDetails", "id", id);
        }
        contactDetailsRepository.deleteById(id);
    }
} 