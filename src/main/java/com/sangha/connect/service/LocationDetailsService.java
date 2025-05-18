package com.sangha.connect.service;

import com.sangha.connect.entity.LocationDetails;
import com.sangha.connect.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocationDetailsService {
    List<LocationDetails> getAllLocationDetails();
    
    LocationDetails getLocationDetailsById(Long id) throws ResourceNotFoundException;
    
    @Transactional
    LocationDetails createLocationDetails(LocationDetails locationDetails);
    
    @Transactional
    LocationDetails updateLocationDetails(Long id, LocationDetails locationDetails) throws ResourceNotFoundException;
    
    @Transactional
    void deleteLocationDetails(Long id) throws ResourceNotFoundException;
} 