package com.sangha.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangha.connect.entity.LocationDetails;
import com.sangha.connect.repository.LocationDetailsRepository;

@Service
public class LocationDetailsService {

    @Autowired
    private LocationDetailsRepository locationDetailsRepository;

    // Create or update location details
    public LocationDetails saveOrUpdateLocationDetails(LocationDetails locationDetails) {
        return locationDetailsRepository.save(locationDetails);
    }

    // Retrieve all location details
    public List<LocationDetails> getAllLocationDetails() {
        return locationDetailsRepository.findAll();
    }

    // Retrieve location details by ID
    public Optional<LocationDetails> getLocationDetailsById(Long id) {
        return locationDetailsRepository.findById(id);
    }

    // Delete location details by ID
    public void deleteLocationDetailsById(Long id) {
        locationDetailsRepository.deleteById(id);
    }
}
