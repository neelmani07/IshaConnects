package com.sangha.connect.controller;

import com.sangha.common.dto.ApiResponse;
import com.sangha.connect.dto.PointWithDistanceDTO;
import com.sangha.connect.entity.Point;
import com.sangha.connect.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
@Tag(name = "Point", description = "Point management APIs")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @Operation(summary = "Get all points")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Point>>> getAllPoints() {
        List<Point> points = pointService.getAllPoints();
        return ResponseEntity.ok(ApiResponse.success("Points retrieved successfully", points));
    }

    @Operation(summary = "Get point by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Point>> getPointById(@Parameter(description = "Point ID") @PathVariable Long id) {
        Point point = pointService.getPointById(id);
        return ResponseEntity.ok(ApiResponse.success("Point retrieved successfully", point));
    }

    @Operation(summary = "Get points by user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Point>>> getPointsByUserId(@Parameter(description = "User ID") @PathVariable Long userId) {
        List<Point> points = pointService.getPointsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Points retrieved successfully", points));
    }

    @Operation(summary = "Get points by user ID and type ID")
    @GetMapping("/user/{userId}/type/{typeId}")
    public ResponseEntity<ApiResponse<List<Point>>> getPointsByUserIdAndTypeId(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Type ID") @PathVariable Long typeId) {
        List<Point> points = pointService.getPointsByUserIdAndTypeId(userId, typeId);
        return ResponseEntity.ok(ApiResponse.success("Points retrieved successfully", points));
    }

    @Operation(summary = "Search points within radius")
    @GetMapping("/search/radius")
    public ResponseEntity<ApiResponse<List<PointWithDistanceDTO>>> searchWithinRadius(
            @Parameter(description = "Latitude of the center point") @RequestParam Double latitude,
            @Parameter(description = "Longitude of the center point") @RequestParam Double longitude,
            @Parameter(description = "Search radius in kilometers") @RequestParam Double radius) {
        List<PointWithDistanceDTO> points = pointService.findPointsWithinRadius(latitude, longitude, radius);
        return ResponseEntity.ok(ApiResponse.success("Points retrieved successfully", points));
    }

    @Operation(summary = "Search points by city with distance")
    @GetMapping("/search/city")
    public ResponseEntity<ApiResponse<List<PointWithDistanceDTO>>> searchByCity(
            @Parameter(description = "City name") @RequestParam String city,
            @Parameter(description = "Latitude of the reference point") @RequestParam Double latitude,
            @Parameter(description = "Longitude of the reference point") @RequestParam Double longitude) {
        List<PointWithDistanceDTO> points = pointService.findPointsByCityWithDistance(city, latitude, longitude);
        return ResponseEntity.ok(ApiResponse.success("Points retrieved successfully", points));
    }

    @Operation(summary = "Search points by state with distance")
    @GetMapping("/search/state")
    public ResponseEntity<ApiResponse<List<PointWithDistanceDTO>>> searchByState(
            @Parameter(description = "State name") @RequestParam String state,
            @Parameter(description = "Latitude of the reference point") @RequestParam Double latitude,
            @Parameter(description = "Longitude of the reference point") @RequestParam Double longitude) {
        List<PointWithDistanceDTO> points = pointService.findPointsByStateWithDistance(state, latitude, longitude);
        return ResponseEntity.ok(ApiResponse.success("Points retrieved successfully", points));
    }

    @Operation(summary = "Create a new point")
    @PostMapping
    public ResponseEntity<ApiResponse<Point>> createPoint(@Parameter(description = "Point details") @RequestBody Point point) {
        Point createdPoint = pointService.createPoint(point);
        return ResponseEntity.ok(ApiResponse.success("Point created successfully", createdPoint));
    }

    @Operation(summary = "Update a point")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Point>> updatePoint(
            @Parameter(description = "Point ID") @PathVariable Long id,
            @Parameter(description = "Updated point details") @RequestBody Point point) {
        Point updatedPoint = pointService.updatePoint(id, point);
        return ResponseEntity.ok(ApiResponse.success("Point updated successfully", updatedPoint));
    }

    @Operation(summary = "Delete a point")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePoint(@Parameter(description = "Point ID") @PathVariable Long id) {
        pointService.deletePoint(id);
        return ResponseEntity.ok(ApiResponse.success("Point deleted successfully", null));
    }
} 