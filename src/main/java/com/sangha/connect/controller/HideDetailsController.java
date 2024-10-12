package com.sangha.connect.controller;

import com.sangha.connect.entity.HideDetails;
import com.sangha.connect.service.HideDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hide-details")
public class HideDetailsController {

    @Autowired
    private HideDetailsService hideDetailsService;

    // Create or update hide details
    @PostMapping
    public ResponseEntity<HideDetails> createOrUpdateHideDetails(@RequestBody HideDetails hideDetails) {
        HideDetails savedHideDetails = hideDetailsService.saveOrUpdateHideDetails(hideDetails);
        return ResponseEntity.ok(savedHideDetails);
    }

    // Retrieve all hide details
    @GetMapping
    public ResponseEntity<List<HideDetails>> getAllHideDetails() {
        List<HideDetails> hideDetails = hideDetailsService.getAllHideDetails();
        return ResponseEntity.ok(hideDetails);
    }

    // Retrieve hide details by ID
    @GetMapping("/{id}")
    public ResponseEntity<HideDetails> getHideDetailsById(@PathVariable Long id) {
        Optional<HideDetails> hideDetails = hideDetailsService.getHideDetailsById(id);
        return hideDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete hide details by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHideDetailsById(@PathVariable Long id) {
        hideDetailsService.deleteHideDetailsById(id);
        return ResponseEntity.noContent().build();
    }
}
