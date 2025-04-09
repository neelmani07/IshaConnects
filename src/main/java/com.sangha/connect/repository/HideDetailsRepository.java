package com.sangha.connect.repository;

import com.sangha.connect.entity.HideDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HideDetailsRepository extends JpaRepository<HideDetails, Long> {
    // Custom query methods can be added here if needed
}
