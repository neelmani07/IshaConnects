package com.sangha.connect.repository;

import com.sangha.connect.entity.LocationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDetailsRepository extends JpaRepository<LocationDetails, Long> {
} 