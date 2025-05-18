package com.sangha.forum.service;

import com.sangha.common.entity.ContactDetails;
import com.sangha.forum.dto.ReportRequestDTO;
import com.sangha.forum.entity.Report;
import com.sangha.forum.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReportService {
    @Transactional
    Report createReport(ReportRequestDTO request, ContactDetails reporter);
    
    @Transactional
    Report updateReportStatus(Long reportId, String status, String moderatorNotes, ContactDetails moderator);
    
    List<Report> getReportsByPost(Long postId);
    
    Page<Report> getPendingReports(Pageable pageable);
    
    Page<Report> getReportsByReporter(Long reporterId, Pageable pageable);
    
    Page<Report> getReportsByModerator(Long moderatorId, Pageable pageable);
} 