package com.sangha.forum.repository;

import com.sangha.forum.entity.Report;
import com.sangha.forum.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findByStatus(ReportStatus status, Pageable pageable);
    List<Report> findByPostId(Long postId);
    Page<Report> findByReporterId(Long reporterId, Pageable pageable);
    Page<Report> findByModeratorId(Long moderatorId, Pageable pageable);
    Optional<Report> findByPostIdAndReporterId(Long postId, Long reporterId);
} 