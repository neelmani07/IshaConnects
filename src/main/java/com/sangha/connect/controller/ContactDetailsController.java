package com.sangha.connect.controller;

import com.sangha.common.dto.ApiResponse;
import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.service.ContactDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact-details")
@Tag(name = "Contact Details", description = "Contact details management APIs")
@RequiredArgsConstructor
public class ContactDetailsController {

    private final ContactDetailsService contactDetailsService;

    @Operation(summary = "Get all contact details")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactDetails>>> getAllContactDetails() {
        List<ContactDetails> contactDetails = contactDetailsService.getAllContactDetails();
        return ResponseEntity.ok(ApiResponse.success("Contact details retrieved successfully", contactDetails));
    }

    @Operation(summary = "Get contact details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactDetails>> getContactDetailsById(@Parameter(description = "Contact details ID") @PathVariable Long id) {
        ContactDetails contactDetails = contactDetailsService.getContactDetailsById(id);
        return ResponseEntity.ok(ApiResponse.success("Contact details retrieved successfully", contactDetails));
    }

    @Operation(summary = "Create new contact details")
    @PostMapping
    public ResponseEntity<ApiResponse<ContactDetails>> createContactDetails(@Parameter(description = "Contact details") @RequestBody ContactDetails contactDetails) {
        ContactDetails createdContactDetails = contactDetailsService.createContactDetails(contactDetails);
        return ResponseEntity.ok(ApiResponse.success("Contact details created successfully", createdContactDetails));
    }

    @Operation(summary = "Update contact details")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactDetails>> updateContactDetails(
            @Parameter(description = "Contact details ID") @PathVariable Long id,
            @Parameter(description = "Updated contact details") @RequestBody ContactDetails contactDetails) {
        ContactDetails updatedContactDetails = contactDetailsService.updateContactDetails(id, contactDetails);
        return ResponseEntity.ok(ApiResponse.success("Contact details updated successfully", updatedContactDetails));
    }

    @Operation(summary = "Delete contact details")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContactDetails(@Parameter(description = "Contact details ID") @PathVariable Long id) {
        contactDetailsService.deleteContactDetails(id);
        return ResponseEntity.ok(ApiResponse.success("Contact details deleted successfully", null));
    }
} 