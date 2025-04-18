package com.sangha.forum.controller;

import com.sangha.connect.entity.ContactDetails;
import com.sangha.forum.dto.ReportRequestDTO;
import com.sangha.forum.entity.Report;
import com.sangha.forum.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Post reporting APIs")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @Operation(summary = "Create a new report")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Report> createReport(
            @RequestBody ReportRequestDTO request,
            @AuthenticationPrincipal ContactDetails reporter) {
        return ResponseEntity.ok(reportService.createReport(request, reporter));
    }

    @PutMapping("/{reportId}/status")
    @Operation(summary = "Update report status")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Report> updateReportStatus(
            @PathVariable Long reportId,
            @RequestParam String status,
            @RequestParam(required = false) String moderatorNotes,
            @AuthenticationPrincipal ContactDetails moderator) {
        return ResponseEntity.ok(reportService.updateReportStatus(reportId, status, moderatorNotes, moderator));
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Get reports for a post")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<List<Report>> getReportsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(reportService.getReportsByPost(postId));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending reports")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Page<Report>> getPendingReports(Pageable pageable) {
        return ResponseEntity.ok(reportService.getPendingReports(pageable));
    }

    @GetMapping("/reporter/{reporterId}")
    @Operation(summary = "Get reports by reporter")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Page<Report>> getReportsByReporter(
            @PathVariable Long reporterId,
            Pageable pageable) {
        return ResponseEntity.ok(reportService.getReportsByReporter(reporterId, pageable));
    }

    @GetMapping("/moderator/{moderatorId}")
    @Operation(summary = "Get reports handled by moderator")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Page<Report>> getReportsByModerator(
            @PathVariable Long moderatorId,
            Pageable pageable) {
        return ResponseEntity.ok(reportService.getReportsByModerator(moderatorId, pageable));
    }
} 