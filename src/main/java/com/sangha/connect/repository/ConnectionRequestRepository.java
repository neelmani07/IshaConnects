package com.sangha.connect.repository;

import com.sangha.connect.entity.ConnectionRequest;
import com.sangha.connect.entity.ConnectionStatus;
import com.sangha.connect.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {
    boolean existsBySenderAndReceiver(ContactDetails sender, ContactDetails receiver);
    List<ConnectionRequest> findByReceiverIdAndStatus(Long receiverId, ConnectionStatus status);
} 