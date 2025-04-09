package com.sangha.connect.repository;

import com.sangha.connect.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

	// Custom query method to find entities by location (example)
	List<Point> findByLocation_CountryAndLocation_State(String country, String state);

	// Custom query method to find entities within a range (latitude, longitude, radius)
	
	@Query(value = "SELECT *, " +
	        "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude)))) AS distance " +
	        "FROM entities " +
            "WHERE state = :state " +  // State-based filtering	        
	        "HAVING distance < :range " +
	        "ORDER BY distance", nativeQuery = true)
	List<Point> getEntitiesByProximity(@Param("latitude") Double latitude,
	                                    @Param("longitude") Double longitude,
	                                    @Param("range") Double range,
										@Param("state") String state);

	// Implementation can be done using custom queries or native SQL
}
