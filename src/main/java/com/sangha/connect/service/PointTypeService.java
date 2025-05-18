package com.sangha.connect.service;

import com.sangha.connect.entity.PointType;
import com.sangha.common.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PointTypeService {
    List<PointType> getAllPointTypes();
    
    PointType getPointTypeById(Long id) throws ResourceNotFoundException;
    
    @Transactional
    PointType createPointType(PointType pointType);
    
    @Transactional
    PointType updatePointType(Long id, PointType pointType) throws ResourceNotFoundException;
    
    @Transactional
    void deletePointType(Long id) throws ResourceNotFoundException;
} 