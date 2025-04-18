package com.sangha.forum.service.impl;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.ReportRequestDTO;
import com.sangha.forum.entity.Post;
import com.sangha.forum.entity.Report;
import com.sangha.forum.entity.ReportStatus;
import com.sangha.forum.exception.ResourceNotFoundException;
import com.sangha.forum.repository.PostRepository;
import com.sangha.forum.repository.ReportRepository;
import com.sangha.forum.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Report createReport(ReportRequestDTO request, ContactDetails reporter) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", request.getPostId()));

        Report report = new Report();
        report.setPost(post);
        report.setReporter(reporter);
        report.setReason(request.getReason());
        report.setStatus(ReportStatus.PENDING);

        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public Report updateReportStatus(Long reportId, String status, String moderatorNotes, ContactDetails moderator) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", reportId));

        report.setStatus(ReportStatus.valueOf(status.toUpperCase()));
        report.setModeratorNotes(moderatorNotes);
        report.setModerator(moderator);

        return reportRepository.save(report);
    }

    @Override
    public List<Report> getReportsByPost(Long postId) {
        return reportRepository.findByPostId(postId);
    }

    @Override
    public Page<Report> getPendingReports(Pageable pageable) {
        return reportRepository.findByStatus(ReportStatus.PENDING, pageable);
    }

    @Override
    public Page<Report> getReportsByReporter(Long reporterId, Pageable pageable) {
        return reportRepository.findByReporterId(reporterId, pageable);
    }

    @Override
    public Page<Report> getReportsByModerator(Long moderatorId, Pageable pageable) {
        return reportRepository.findByModeratorId(moderatorId, pageable);
    }
} 