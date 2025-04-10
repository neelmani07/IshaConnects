package com.sangha.connect.controller;

import com.sangha.common.dto.ApiResponse;
import com.sangha.connect.entity.LocationDetails;
import com.sangha.connect.service.LocationDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location-details")
@Tag(name = "Location Details", description = "Location details management APIs")
@RequiredArgsConstructor
public class LocationDetailsController {

    private final LocationDetailsService locationDetailsService;

    @Operation(summary = "Get all location details")
    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationDetails>>> getAllLocationDetails() {
        List<LocationDetails> locationDetails = locationDetailsService.getAllLocationDetails();
        return ResponseEntity.ok(ApiResponse.success("Location details retrieved successfully", locationDetails));
    }

    @Operation(summary = "Get location details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationDetails>> getLocationDetailsById(@Parameter(description = "Location details ID") @PathVariable Long id) {
        LocationDetails locationDetails = locationDetailsService.getLocationDetailsById(id);
        return ResponseEntity.ok(ApiResponse.success("Location details retrieved successfully", locationDetails));
    }

    @Operation(summary = "Create new location details")
    @PostMapping
    public ResponseEntity<ApiResponse<LocationDetails>> createLocationDetails(@Parameter(description = "Location details") @RequestBody LocationDetails locationDetails) {
        LocationDetails createdLocationDetails = locationDetailsService.createLocationDetails(locationDetails);
        return ResponseEntity.ok(ApiResponse.success("Location details created successfully", createdLocationDetails));
    }

    @Operation(summary = "Update location details")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationDetails>> updateLocationDetails(
            @Parameter(description = "Location details ID") @PathVariable Long id,
            @Parameter(description = "Updated location details") @RequestBody LocationDetails locationDetails) {
        LocationDetails updatedLocationDetails = locationDetailsService.updateLocationDetails(id, locationDetails);
        return ResponseEntity.ok(ApiResponse.success("Location details updated successfully", updatedLocationDetails));
    }

    @Operation(summary = "Delete location details")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLocationDetails(@Parameter(description = "Location details ID") @PathVariable Long id) {
        locationDetailsService.deleteLocationDetails(id);
        return ResponseEntity.ok(ApiResponse.success("Location details deleted successfully", null));
    }
} 