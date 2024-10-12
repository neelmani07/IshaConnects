package com.sangha.connect.service;

import com.sangha.connect.entity.HideDetails;
import com.sangha.connect.repository.HideDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HideDetailsService {

    @Autowired
    private HideDetailsRepository hideDetailsRepository;

    // Create or update hide details
    public HideDetails saveOrUpdateHideDetails(HideDetails hideDetails) {
        return hideDetailsRepository.save(hideDetails);
    }

    // Retrieve all hide details
    public List<HideDetails> getAllHideDetails() {
        return hideDetailsRepository.findAll();
    }

    // Retrieve hide details by ID
    public Optional<HideDetails> getHideDetailsById(Long id) {
        return hideDetailsRepository.findById(id);
    }

    // Delete hide details by ID
    public void deleteHideDetailsById(Long id) {
        hideDetailsRepository.deleteById(id);
    }
}
