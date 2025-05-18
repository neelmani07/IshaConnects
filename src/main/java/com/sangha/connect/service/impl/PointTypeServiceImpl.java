package com.sangha.connect.service.impl;

import com.sangha.connect.entity.PointType;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.PointTypeRepository;
import com.sangha.connect.service.PointTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointTypeServiceImpl implements PointTypeService {

    private final PointTypeRepository pointTypeRepository;

    @Override
    public List<PointType> getAllPointTypes() {
        return pointTypeRepository.findAll();
    }

    @Override
    public PointType getPointTypeById(Long id) throws ResourceNotFoundException {
        return pointTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PointType", "id", id));
    }

    @Override
    @Transactional
    public PointType createPointType(PointType pointType) {
        return pointTypeRepository.save(pointType);
    }

    @Override
    @Transactional
    public PointType updatePointType(Long id, PointType pointType) throws ResourceNotFoundException {
        if (!pointTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("PointType", "id", id);
        }
        pointType.setId(id);
        return pointTypeRepository.save(pointType);
    }

    @Override
    @Transactional
    public void deletePointType(Long id) throws ResourceNotFoundException {
        if (!pointTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("PointType", "id", id);
        }
        pointTypeRepository.deleteById(id);
    }
} 