package com.sangha.connect.repository;

import com.sangha.connect.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    // Custom query methods can be added here if needed
}
