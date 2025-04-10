package com.sangha.connect.repository;

import com.sangha.connect.entity.PointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointTypeRepository extends JpaRepository<PointType, Long> {
} 