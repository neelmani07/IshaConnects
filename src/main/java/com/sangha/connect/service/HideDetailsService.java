package com.sangha.connect.service;

import com.sangha.connect.entity.HideDetails;
import com.sangha.common.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HideDetailsService {
    List<HideDetails> getAllHideDetails();
    
    HideDetails getHideDetailsById(Long id) throws ResourceNotFoundException;
    
    @Transactional
    HideDetails createHideDetails(HideDetails hideDetails);
    
    @Transactional
    HideDetails updateHideDetails(Long id, HideDetails hideDetails) throws ResourceNotFoundException;
    
    @Transactional
    void deleteHideDetails(Long id) throws ResourceNotFoundException;
} 