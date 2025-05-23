package com.sangha.connect.repository;

import com.sangha.connect.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    Optional<ContactDetails> findByEmail(String email);
    List<ContactDetails> findByEmailContainingIgnoreCaseOrNameContainingIgnoreCase(String email, String name);
} 