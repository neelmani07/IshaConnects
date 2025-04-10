package com.sangha.connect.service;

import com.sangha.connect.entity.Point;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    public Point getPointById(Long id) {
        return pointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Point", "id", id));
    }

    public List<Point> getPointsByUserId(Long userId) {
        return pointRepository.findByContactDetailsId(userId);
    }

    public List<Point> getPointsByUserIdAndTypeId(Long userId, Long typeId) {
        return pointRepository.findByContactDetailsIdAndPointTypeId(userId, typeId);
    }

    @Transactional
    public Point createPoint(Point point) {
        return pointRepository.save(point);
    }

    @Transactional
    public Point updatePoint(Long id, Point point) {
        if (!pointRepository.existsById(id)) {
            throw new ResourceNotFoundException("Point", "id", id);
        }
        point.setId(id);
        return pointRepository.save(point);
    }

    @Transactional
    public void deletePoint(Long id) {
        if (!pointRepository.existsById(id)) {
            throw new ResourceNotFoundException("Point", "id", id);
        }
        pointRepository.deleteById(id);
    }
} 