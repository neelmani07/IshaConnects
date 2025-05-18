package com.sangha.common.repository;

import com.sangha.common.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    Optional<ContactDetails> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<ContactDetails> findByResetToken(String token);
} 