package com.sangha.connect.service;

import com.sangha.connect.dto.ConnectionNotification;
import com.sangha.connect.dto.ConnectionRejectionNotification;
import com.sangha.connect.dto.ConnectionRequestNotification;
import com.sangha.connect.entity.Connection;
import com.sangha.connect.entity.ConnectionRequest;
import com.sangha.connect.entity.ConnectionStatus;
import com.sangha.connect.entity.ContactDetails;
import com.sangha.connect.exception.BadRequestException;
import com.sangha.connect.exception.ResourceNotFoundException;
import com.sangha.connect.repository.ConnectionRepository;
import com.sangha.connect.repository.ConnectionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectionService {
    private final ConnectionRequestRepository connectionRequestRepository;
    private final ConnectionRepository connectionRepository;
    private final ContactDetailsService contactDetailsService;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Transactional
    public ConnectionRequest sendRequest(Long senderId, Long receiverId, String message) {
        ContactDetails sender = contactDetailsService.getContactDetailsById(senderId);
        ContactDetails receiver = contactDetailsService.getContactDetailsById(receiverId);
        
        // Check if request already exists
        if (connectionRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new BadRequestException("Connection request already sent");
        }
        
        // Check if already connected
        if (connectionRepository.existsByUser1AndUser2(sender, receiver)) {
            throw new BadRequestException("Already connected");
        }
        
        ConnectionRequest request = new ConnectionRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setMessage(message);
        request.setStatus(ConnectionStatus.PENDING);
        request = connectionRequestRepository.save(request);
        
        // Send real-time notification
        sendConnectionRequestNotification(receiver, sender.getName(), message);
        
        return request;
    }
    
    @Transactional
    public Connection acceptRequest(Long requestId) {
        ConnectionRequest request = getRequest(requestId);
        request.setStatus(ConnectionStatus.ACCEPTED);
        connectionRequestRepository.save(request);
        
        // Create connection
        Connection connection = new Connection();
        connection.setUser1(request.getSender());
        connection.setUser2(request.getReceiver());
        connection = connectionRepository.save(connection);
        
        // Send notifications to both users
        notifyConnectionEstablished(request.getSender(), request.getReceiver());
        notifyConnectionEstablished(request.getReceiver(), request.getSender());
        
        return connection;
    }
    
    @Transactional
    public void rejectRequest(Long requestId) {
        ConnectionRequest request = getRequest(requestId);
        request.setStatus(ConnectionStatus.REJECTED);
        connectionRequestRepository.save(request);
        
        // Notify sender about rejection
        notifyRequestRejected(request.getSender(), request.getReceiver().getName());
    }
    
    public List<ConnectionRequest> getPendingRequests(Long userId) {
        return connectionRequestRepository.findByReceiverIdAndStatus(userId, ConnectionStatus.PENDING);
    }
    
    public List<ContactDetails> getConnections(Long userId) {
        return connectionRepository.findConnectedUsers(userId);
    }
    
    private ConnectionRequest getRequest(Long requestId) {
        return connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("ConnectionRequest", "id", requestId));
    }
    
    private void sendConnectionRequestNotification(ContactDetails receiver, String senderName, String message) {
        messagingTemplate.convertAndSendToUser(
            receiver.getId().toString(),
            "/queue/connection-requests",
            new ConnectionRequestNotification(senderName, message)
        );
    }
    
    private void notifyConnectionEstablished(ContactDetails user, ContactDetails connectedUser) {
        messagingTemplate.convertAndSendToUser(
            user.getId().toString(),
            "/queue/connections",
            new ConnectionNotification(connectedUser.getName())
        );
    }
    
    private void notifyRequestRejected(ContactDetails sender, String receiverName) {
        messagingTemplate.convertAndSendToUser(
            sender.getId().toString(),
            "/queue/connection-requests",
            new ConnectionRejectionNotification(receiverName)
        );
    }
} 