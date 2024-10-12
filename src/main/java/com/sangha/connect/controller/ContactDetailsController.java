package com.sangha.connect.controller;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.service.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact-details")
public class ContactDetailsController {

    @Autowired
    private ContactDetailsService contactDetailsService;

    // Create or update contact details
    @PostMapping
    public ResponseEntity<ContactDetails> createOrUpdateContactDetails(@RequestBody ContactDetails contactDetails) {
        ContactDetails savedContactDetails = contactDetailsService.saveOrUpdateContactDetails(contactDetails);
        return ResponseEntity.ok(savedContactDetails);
    }

    // Retrieve all contact details
    @GetMapping
    public ResponseEntity<List<ContactDetails>> getAllContactDetails() {
        List<ContactDetails> contactDetails = contactDetailsService.getAllContactDetails();
        return ResponseEntity.ok(contactDetails);
    }

    // Retrieve contact details by ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDetails> getContactDetailsById(@PathVariable Long id) {
        Optional<ContactDetails> contactDetails = contactDetailsService.getContactDetailsById(id);
        return contactDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete contact details by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactDetailsById(@PathVariable Long id) {
        contactDetailsService.deleteContactDetailsById(id);
        return ResponseEntity.noContent().build();
    }
}
