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

    @Query(value = """
        SELECT p.*, 
               (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * 
               cos(radians(p.longitude) - radians(:longitude)) + 
               sin(radians(:latitude)) * sin(radians(p.latitude)))) AS distance
        FROM point p
        WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * 
              cos(radians(p.longitude) - radians(:longitude)) + 
              sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius
        ORDER BY distance
        """, nativeQuery = true)
    List<Point> findPointsWithinRadius(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Double radius);

    @Query(value = """
        SELECT p.*, 
               (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * 
               cos(radians(p.longitude) - radians(:longitude)) + 
               sin(radians(:latitude)) * sin(radians(p.latitude)))) AS distance
        FROM point p
        JOIN location_details l ON p.location_id = l.id
        WHERE l.city = :city
        ORDER BY distance
        """, nativeQuery = true)
    List<Point> findPointsByCityWithDistance(
            @Param("city") String city,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude);

    @Query(value = """
        SELECT p.*, 
               (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * 
               cos(radians(p.longitude) - radians(:longitude)) + 
               sin(radians(:latitude)) * sin(radians(p.latitude)))) AS distance
        FROM point p
        JOIN location_details l ON p.location_id = l.id
        WHERE l.state = :state
        ORDER BY distance
        """, nativeQuery = true)
    List<Point> findPointsByStateWithDistance(
            @Param("state") String state,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude);
} 