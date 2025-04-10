package com.sangha.connect.repository;

import com.sangha.connect.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByContactDetailsId(Long contactDetailsId);
    
    @Query("SELECT p FROM Point p WHERE p.contactDetails.id = :contactDetailsId AND p.pointType.id = :pointTypeId")
    List<Point> findByContactDetailsIdAndPointTypeId(@Param("contactDetailsId") Long contactDetailsId, @Param("pointTypeId") Long pointTypeId);
} 