package com.sangha.connect.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangha.connect.entity.Point;
import com.sangha.connect.service.PointService;

@RestController
@RequestMapping("/api/entities")
public class PointController {

    @Autowired
    private PointService pointService;

    // Create or update an entity
    @PostMapping
    public ResponseEntity<Point> createOrUpdateEntity(@RequestBody Point point) {
        Point savedEntity = pointService.saveOrUpdateEntity(point);
        return ResponseEntity.ok(savedEntity);
    }

    // Retrieve all entities
    @GetMapping
    public ResponseEntity<List<Point>> getAllEntities() {
        List<Point> entities = pointService.getAllEntities();
        return ResponseEntity.ok(entities);
    }

    // Retrieve an entity by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Point> getEntityById(@PathVariable Long id) {
        Optional<Point> entity = pointService.getEntityById(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an entity by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntityById(@PathVariable Long id) {
    	pointService.deleteEntityById(id);
        return ResponseEntity.noContent().build();
    }

    // Retrieve entities by country and state
    @GetMapping("/search/location")
    public ResponseEntity<List<Point>> getEntitiesByLocation(@RequestParam String country, @RequestParam String state) {
        List<Point> entities = pointService.getEntitiesByLocation(country, state);
        return ResponseEntity.ok(entities);
    }

    // Additional endpoint for searching entities by proximity (latitude, longitude, range)
    @GetMapping("/search/proximity")
    public ResponseEntity<List<Point>> getEntitiesByProximity(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam Double range, @RequestParam String state) {
        List<Point> entities = pointService.getEntitiesByProximity(latitude, longitude, range, state);
        return ResponseEntity.ok(entities);
    }
}
