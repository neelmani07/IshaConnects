package com.sangha.connect.service;

import com.sangha.connect.entity.Connection;
import com.sangha.connect.entity.ConnectionRequest;
import com.sangha.common.entity.ContactDetails;
import com.sangha.connect.exception.BadRequestException;
import com.sangha.connect.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConnectionService {
    @Transactional
    ConnectionRequest sendRequest(Long senderId, Long receiverId, String message) throws BadRequestException;
    
    @Transactional
    Connection acceptRequest(Long requestId) throws ResourceNotFoundException;
    
    @Transactional
    void rejectRequest(Long requestId) throws ResourceNotFoundException;
    
    List<ConnectionRequest> getPendingRequests(Long userId);
    
    List<ContactDetails> getConnections(Long userId);
} 