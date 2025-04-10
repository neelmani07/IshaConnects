package com.sangha.connect.service;

import com.sangha.connect.entity.HideDetails;
import com.sangha.connect.repository.HideDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HideDetailsService {

    @Autowired
    private HideDetailsRepository hideDetailsRepository;

    public List<HideDetails> getAllHideDetails() {
        return hideDetailsRepository.findAll();
    }

    public HideDetails getHideDetailsById(Long id) {
        return hideDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HideDetails not found with id: " + id));
    }

    public HideDetails createHideDetails(HideDetails hideDetails) {
        return hideDetailsRepository.save(hideDetails);
    }

    public HideDetails updateHideDetails(Long id, HideDetails hideDetails) {
        if (!hideDetailsRepository.existsById(id)) {
            throw new RuntimeException("HideDetails not found with id: " + id);
        }
        hideDetails.setId(id);
        return hideDetailsRepository.save(hideDetails);
    }

    public void deleteHideDetails(Long id) {
        hideDetailsRepository.deleteById(id);
    }
} 