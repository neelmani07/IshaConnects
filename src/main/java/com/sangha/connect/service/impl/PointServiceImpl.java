package com.sangha.connect.service.impl;

import com.sangha.connect.dto.PointWithDistanceDTO;
import com.sangha.connect.entity.Point;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.PointRepository;
import com.sangha.connect.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }

    @Override
    public Point getPointById(Long id) throws ResourceNotFoundException {
        return pointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Point", "id", id));
    }

    @Override
    public List<Point> getPointsByUserId(Long userId) {
        return pointRepository.findByContactDetailsId(userId);
    }

    @Override
    public List<Point> getPointsByUserIdAndTypeId(Long userId, Long typeId) {
        return pointRepository.findByContactDetailsIdAndPointTypeId(userId, typeId);
    }

    @Override
    @Transactional
    public Point createPoint(Point point) {
        return pointRepository.save(point);
    }

    @Override
    @Transactional
    public Point updatePoint(Long id, Point point) throws ResourceNotFoundException {
        if (!pointRepository.existsById(id)) {
            throw new ResourceNotFoundException("Point", "id", id);
        }
        point.setId(id);
        return pointRepository.save(point);
    }

    @Override
    @Transactional
    public void deletePoint(Long id) throws ResourceNotFoundException {
        if (!pointRepository.existsById(id)) {
            throw new ResourceNotFoundException("Point", "id", id);
        }
        pointRepository.deleteById(id);
    }

    @Override
    public List<PointWithDistanceDTO> findPointsWithinRadius(Double latitude, Double longitude, Double radius) {
        List<Point> points = pointRepository.findPointsWithinRadius(latitude, longitude, radius);
        return points.stream()
                .map(point -> PointWithDistanceDTO.builder()
                        .point(point)
                        .distance(calculateDistance(latitude, longitude, point.getLatitude(), point.getLongitude()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<PointWithDistanceDTO> findPointsByCityWithDistance(String city, Double latitude, Double longitude) {
        List<Point> points = pointRepository.findPointsByCityWithDistance(city, latitude, longitude);
        return points.stream()
                .map(point -> PointWithDistanceDTO.builder()
                        .point(point)
                        .distance(calculateDistance(latitude, longitude, point.getLatitude(), point.getLongitude()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<PointWithDistanceDTO> findPointsByStateWithDistance(String state, Double latitude, Double longitude) {
        List<Point> points = pointRepository.findPointsByStateWithDistance(state, latitude, longitude);
        return points.stream()
                .map(point -> PointWithDistanceDTO.builder()
                        .point(point)
                        .distance(calculateDistance(latitude, longitude, point.getLatitude(), point.getLongitude()))
                        .build())
                .collect(Collectors.toList());
    }

    private Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
} 