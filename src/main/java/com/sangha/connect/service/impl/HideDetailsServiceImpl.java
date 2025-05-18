package com.sangha.connect.service.impl;

import com.sangha.connect.entity.HideDetails;
import com.sangha.common.exception.ResourceNotFoundException;
import com.sangha.connect.repository.HideDetailsRepository;
import com.sangha.connect.service.HideDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HideDetailsServiceImpl implements HideDetailsService {

    private final HideDetailsRepository hideDetailsRepository;

    @Override
    public List<HideDetails> getAllHideDetails() {
        return hideDetailsRepository.findAll();
    }

    @Override
    public HideDetails getHideDetailsById(Long id) throws ResourceNotFoundException {
        return hideDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HideDetails", "id", id));
    }

    @Override
    @Transactional
    public HideDetails createHideDetails(HideDetails hideDetails) {
        return hideDetailsRepository.save(hideDetails);
    }

    @Override
    @Transactional
    public HideDetails updateHideDetails(Long id, HideDetails hideDetails) throws ResourceNotFoundException {
        if (!hideDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("HideDetails", "id", id);
        }
        hideDetails.setId(id);
        return hideDetailsRepository.save(hideDetails);
    }

    @Override
    @Transactional
    public void deleteHideDetails(Long id) throws ResourceNotFoundException {
        if (!hideDetailsRepository.existsById(id)) {
            throw new ResourceNotFoundException("HideDetails", "id", id);
        }
        hideDetailsRepository.deleteById(id);
    }
} 