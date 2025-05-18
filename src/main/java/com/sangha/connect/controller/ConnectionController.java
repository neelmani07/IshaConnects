package com.sangha.connect.controller;

import com.sangha.common.dto.ApiResponse;
import com.sangha.connect.entity.Connection;
import com.sangha.connect.entity.ConnectionRequest;
import com.sangha.common.entity.ContactDetails;
import com.sangha.connect.service.ConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/connections")
@Tag(name = "Connections", description = "Connection management APIs")
@RequiredArgsConstructor
public class ConnectionController {
    private final ConnectionService connectionService;
    
    @Operation(summary = "Send connection request")
    @PostMapping("/requests")
    public ResponseEntity<ApiResponse<ConnectionRequest>> sendRequest(
            @Parameter(description = "Receiver ID") @RequestParam Long receiverId,
            @Parameter(description = "Optional message") @RequestParam(required = false) String message) {
        Long senderId = getCurrentUserId();
        ConnectionRequest request = connectionService.sendRequest(senderId, receiverId, message);
        return ResponseEntity.ok(ApiResponse.success("Connection request sent", request));
    }
    
    @Operation(summary = "Accept connection request")
    @PostMapping("/requests/{requestId}/accept")
    public ResponseEntity<ApiResponse<Connection>> acceptRequest(
            @Parameter(description = "Request ID") @PathVariable Long requestId) {
        Connection connection = connectionService.acceptRequest(requestId);
        return ResponseEntity.ok(ApiResponse.success("Connection request accepted", connection));
    }
    
    @Operation(summary = "Reject connection request")
    @PostMapping("/requests/{requestId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectRequest(
            @Parameter(description = "Request ID") @PathVariable Long requestId) {
        connectionService.rejectRequest(requestId);
        return ResponseEntity.ok(ApiResponse.success("Connection request rejected", null));
    }
    
    @Operation(summary = "Get pending connection requests")
    @GetMapping("/requests/pending")
    public ResponseEntity<ApiResponse<List<ConnectionRequest>>> getPendingRequests() {
        Long userId = getCurrentUserId();
        List<ConnectionRequest> requests = connectionService.getPendingRequests(userId);
        return ResponseEntity.ok(ApiResponse.success("Pending requests retrieved", requests));
    }
    
    @Operation(summary = "Get all connections")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactDetails>>> getConnections() {
        Long userId = getCurrentUserId();
        List<ContactDetails> connections = connectionService.getConnections(userId);
        return ResponseEntity.ok(ApiResponse.success("Connections retrieved", connections));
    }
    
    private Long getCurrentUserId() {
        // TODO: Implement getting current user ID from security context
        return 1L; // Placeholder
    }
} 