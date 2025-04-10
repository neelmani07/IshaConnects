package com.sangha.connect.service;

import com.sangha.connect.entity.LocationDetails;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.LocationDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationDetailsService {

    private final LocationDetailsRepository locationDetailsRepository;

    public List<LocationDetails> getAllLocationDetails() {
        return locationDetailsRepository.findAll();
    }

    public LocationDetails getLocationDetailsById(Long id) {
        return locationDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LocationDetails", "id", id));
    }

    @Transactional
    public LocationDetails createLocationDetails(LocationDetails locationDetails) {
        return locationDetailsRepository.save(locationDetails);
    }

    @Transactional
    public LocationDetails updateLocationDetails(Long id, LocationDetails locationDetails) {
        if (!locationDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("LocationDetails", "id", id);
        }
        locationDetails.setId(id);
        return locationDetailsRepository.save(locationDetails);
    }

    @Transactional
    public void deleteLocationDetails(Long id) {
        if (!locationDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("LocationDetails", "id", id);
        }
        locationDetailsRepository.deleteById(id);
    }
} 