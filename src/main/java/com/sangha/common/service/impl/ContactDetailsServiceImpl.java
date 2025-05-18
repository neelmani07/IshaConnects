package com.sangha.common.service.impl;

import com.sangha.common.entity.ContactDetails;
import com.sangha.common.exception.ResourceNotFoundException;
import com.sangha.common.repository.ContactDetailsRepository;
import com.sangha.common.service.ContactDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final ContactDetailsRepository contactDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ContactDetails user = contactDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new User(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }

    @Override
    public ContactDetails save(ContactDetails user) {
        return contactDetailsRepository.save(user);
    }

    @Override
    public ContactDetails findByEmail(String email) {
        return contactDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

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