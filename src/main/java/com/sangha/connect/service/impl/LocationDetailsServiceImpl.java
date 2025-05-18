package com.sangha.connect.service.impl;

import com.sangha.connect.entity.LocationDetails;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.LocationDetailsRepository;
import com.sangha.connect.service.LocationDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationDetailsServiceImpl implements LocationDetailsService {

    private final LocationDetailsRepository locationDetailsRepository;

    @Override
    public List<LocationDetails> getAllLocationDetails() {
        return locationDetailsRepository.findAll();
    }

    @Override
    public LocationDetails getLocationDetailsById(Long id) throws ResourceNotFoundException {
        return locationDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LocationDetails", "id", id));
    }

    @Override
    @Transactional
    public LocationDetails createLocationDetails(LocationDetails locationDetails) {
        return locationDetailsRepository.save(locationDetails);
    }

    @Override
    @Transactional
    public LocationDetails updateLocationDetails(Long id, LocationDetails locationDetails) throws ResourceNotFoundException {
        if (!locationDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("LocationDetails", "id", id);
        }
        locationDetails.setId(id);
        return locationDetailsRepository.save(locationDetails);
    }

    @Override
    @Transactional
    public void deleteLocationDetails(Long id) throws ResourceNotFoundException {
        if (!locationDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("LocationDetails", "id", id);
        }
        locationDetailsRepository.deleteById(id);
    }
} 