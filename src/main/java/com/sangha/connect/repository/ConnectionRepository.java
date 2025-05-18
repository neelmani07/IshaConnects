package com.sangha.connect.repository;

import com.sangha.connect.entity.Connection;
import com.sangha.common.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    boolean existsByUser1AndUser2(ContactDetails user1, ContactDetails user2);
    
    @Query("SELECT CASE WHEN c.user1.id = :userId THEN c.user2 ELSE c.user1 END " +
           "FROM Connection c " +
           "WHERE c.user1.id = :userId OR c.user2.id = :userId")
    List<ContactDetails> findConnectedUsers(@Param("userId") Long userId);
} 