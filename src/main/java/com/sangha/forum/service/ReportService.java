package com.sangha.forum.service;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.ReportRequestDTO;
import com.sangha.forum.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    Report createReport(ReportRequestDTO request, ContactDetails reporter);
    Report updateReportStatus(Long reportId, String status, String moderatorNotes, ContactDetails moderator);
    List<Report> getReportsByPost(Long postId);
    Page<Report> getPendingReports(Pageable pageable);
    Page<Report> getReportsByReporter(Long reporterId, Pageable pageable);
    Page<Report> getReportsByModerator(Long moderatorId, Pageable pageable);
} 