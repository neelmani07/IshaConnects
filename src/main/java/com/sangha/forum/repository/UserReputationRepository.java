package com.sangha.forum.repository;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.entity.UserReputation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReputationRepository extends JpaRepository<UserReputation, Long> {
    Optional<UserReputation> findByUser(ContactDetails user);
} 