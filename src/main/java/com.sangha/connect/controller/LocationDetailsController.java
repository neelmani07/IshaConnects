package com.sangha.connect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangha.connect.entity.LocationDetails;
import com.sangha.connect.service.LocationDetailsService;

@RestController
@RequestMapping("/api/location-details")
public class LocationDetailsController {

    @Autowired
    private LocationDetailsService locationDetailsService;

    // Create or update location details
    @PostMapping
    public ResponseEntity<LocationDetails> createOrUpdateLocationDetails(@RequestBody LocationDetails locationDetails) {
        LocationDetails savedLocationDetails = locationDetailsService.saveOrUpdateLocationDetails(locationDetails);
        return ResponseEntity.ok(savedLocationDetails);
    }

    // Retrieve all location details
    @GetMapping
    public ResponseEntity<List<LocationDetails>> getAllLocationDetails() {
        List<LocationDetails> locationDetails = locationDetailsService.getAllLocationDetails();
        return ResponseEntity.ok(locationDetails);
    }

    // Retrieve location details by ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDetails> getLocationDetailsById(@PathVariable Long id) {
        Optional<LocationDetails> locationDetails = locationDetailsService.getLocationDetailsById(id);
        return locationDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete location details by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationDetailsById(@PathVariable Long id) {
        locationDetailsService.deleteLocationDetailsById(id);
        return ResponseEntity.noContent().build();
    }
}
