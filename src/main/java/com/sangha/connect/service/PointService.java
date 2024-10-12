package com.sangha.connect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangha.connect.entity.Point;
import com.sangha.connect.repository.PointRepository;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    // Create or update an entity
    public Point saveOrUpdateEntity(Point entity) {
        return pointRepository.save(entity);
    }

    // Retrieve all entities
    public List<Point> getAllEntities() {
        return pointRepository.findAll();
    }

    // Retrieve an entity by its ID
    public Optional<Point> getEntityById(Long id) {
        return pointRepository.findById(id);
    }

    // Delete an entity by its ID
    public void deleteEntityById(Long id) {
        pointRepository.deleteById(id);
    }

    // Retrieve entities by country and state
    //optimization possible
    public List<Point> getEntitiesByLocation(String country, String state) {
        return pointRepository.findByLocation_CountryAndLocation_State(country, state);
    }

	public List<Point> getEntitiesByProximity(Double latitude, Double longitude, Double range, String state) {
		return pointRepository.getEntitiesByProximity(latitude, longitude, range, state);
	}
    // You can add more methods for complex queries, such as finding entities within a specific radius
}
