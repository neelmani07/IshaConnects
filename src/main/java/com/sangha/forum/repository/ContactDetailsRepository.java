package com.sangha.forum.repository;

import com.sangha.connect.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    Optional<ContactDetails> findByEmail(String email);
} 