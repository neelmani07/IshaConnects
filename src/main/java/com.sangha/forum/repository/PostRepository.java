package com.sangha.forum.repository;

import com.sangha.forum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Search posts by keyword in title or content
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post> searchByKeyword(String keyword);

    // Filter posts by location (state, city)
    List<Post> findByStateAndCity(String state, String city);

    // Sort posts by date or points
    List<Post> findAllByOrderByCreatedAtDesc(); // Newest first
    List<Post> findAllByOrderByPointsDesc(); // Most upvoted first
}
