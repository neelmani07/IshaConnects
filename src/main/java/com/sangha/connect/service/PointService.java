package com.sangha.connect.service;

import com.sangha.connect.dto.PointWithDistanceDTO;
import com.sangha.connect.entity.Point;
import com.sangha.connect.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PointService {
    List<Point> getAllPoints();
    Point getPointById(Long id) throws ResourceNotFoundException;
    List<Point> getPointsByUserId(Long userId);
    List<Point> getPointsByUserIdAndTypeId(Long userId, Long typeId);
    
    @Transactional
    Point createPoint(Point point);
    
    @Transactional
    Point updatePoint(Long id, Point point) throws ResourceNotFoundException;
    
    @Transactional
    void deletePoint(Long id) throws ResourceNotFoundException;

    List<PointWithDistanceDTO> findPointsWithinRadius(Double latitude, Double longitude, Double radius);
    List<PointWithDistanceDTO> findPointsByCityWithDistance(String city, Double latitude, Double longitude);
    List<PointWithDistanceDTO> findPointsByStateWithDistance(String state, Double latitude, Double longitude);
} 