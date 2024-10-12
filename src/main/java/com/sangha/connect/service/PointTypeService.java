package com.sangha.connect.service;

import com.sangha.connect.entity.PointType;
import com.sangha.connect.repository.PointTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointTypeService {

    @Autowired
    private PointTypeRepository entityTypeRepository;

    // Create or update entity type
    public PointType saveOrUpdateEntityType(PointType entityType) {
        return entityTypeRepository.save(entityType);
    }

    // Retrieve all entity types
    public List<PointType> getAllEntityTypes() {
        return entityTypeRepository.findAll();
    }

    // Retrieve entity type by ID
    public Optional<PointType> getEntityTypeById(Long id) {
        return entityTypeRepository.findById(id);
    }

    // Delete entity type by ID
    public void deleteEntityTypeById(Long id) {
        entityTypeRepository.deleteById(id);
    }
}
