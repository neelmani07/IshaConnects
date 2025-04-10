package com.sangha.connect.service;

import com.sangha.connect.entity.PointType;
import com.sangha.connect.repository.PointTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointTypeService {

    @Autowired
    private PointTypeRepository pointTypeRepository;

    public List<PointType> getAllPointTypes() {
        return pointTypeRepository.findAll();
    }

    public PointType getPointTypeById(Long id) {
        return pointTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PointType not found with id: " + id));
    }

    public PointType createPointType(PointType pointType) {
        return pointTypeRepository.save(pointType);
    }

    public PointType updatePointType(Long id, PointType pointType) {
        if (!pointTypeRepository.existsById(id)) {
            throw new RuntimeException("PointType not found with id: " + id);
        }
        pointType.setId(id);
        return pointTypeRepository.save(pointType);
    }

    public void deletePointType(Long id) {
        pointTypeRepository.deleteById(id);
    }
} 