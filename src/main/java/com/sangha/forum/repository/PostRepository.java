package com.sangha.forum.repository;

import com.sangha.forum.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Advanced search in both title and content
    @Query("SELECT p FROM Post p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Post> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Combined filters with location and date range
    @Query("SELECT p FROM Post p WHERE " +
           "(:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:state IS NULL OR p.point.location.state = :state) AND " +
           "(:city IS NULL OR p.point.location.city = :city) AND " +
           "(:startDate IS NULL OR p.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR p.createdAt <= :endDate)")
    Page<Post> searchWithFilters(
            @Param("keyword") String keyword,
            @Param("state") String state,
            @Param("city") String city,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    // Sort by multiple criteria
    @Query("SELECT p FROM Post p ORDER BY " +
           "CASE WHEN :sortBy = 'newest' THEN p.createdAt END DESC, " +
           "CASE WHEN :sortBy = 'oldest' THEN p.createdAt END ASC, " +
           "CASE WHEN :sortBy = 'votes' THEN (SELECT COUNT(v) FROM PostVotes v WHERE v.post = p AND v.voteType = 'UP') END DESC, " +
           "CASE WHEN :sortBy = 'comments' THEN (SELECT COUNT(c) FROM Comment c WHERE c.post = p) END DESC")
    Page<Post> findAllSorted(@Param("sortBy") String sortBy, Pageable pageable);

    // Find posts by user
    Page<Post> findByCreatedById(Long userId, Pageable pageable);

    // Find posts with minimum votes
    @Query("SELECT p FROM Post p WHERE " +
           "(SELECT COUNT(v) FROM PostVotes v WHERE v.post = p AND v.voteType = 'UP') >= :minVotes")
    Page<Post> findByMinVotes(@Param("minVotes") int minVotes, Pageable pageable);
}
