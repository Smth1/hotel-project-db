package com.roma.db.service;

import com.roma.db.model.CleaningReport;
import com.roma.db.model.Room;
import com.roma.db.repository.CleaningReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final CleaningReportRepository cleaningReportRepository;

    @Autowired
    public AdminService(CleaningReportRepository cleaningReportRepository) {
        this.cleaningReportRepository = cleaningReportRepository;
    }

    public List<CleaningReport> unClosed() {
        List<CleaningReport> cleaningReports = this.cleaningReportRepository.findAll().stream()
                .filter(el -> !el.getClosed())
                .collect(Collectors.toList());

        return cleaningReports;
    }

    public Page<CleaningReport> findPaginatedUnclosed(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<CleaningReport> list;
        List<CleaningReport> reports = this.unClosed();

        if (reports.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, reports.size());
            list = reports.subList(startItem, toIndex);
        }

        Page<CleaningReport> reportPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), reports.size());

        return reportPage;
    }

    public void confirm(String reportId) {
        Optional<CleaningReport> byId = cleaningReportRepository.findById(Integer.parseInt(reportId));

        if (byId.isPresent()) {
            CleaningReport cleaningReport = byId.get();
            cleaningReport.setClosed(true);
            cleaningReportRepository.save(cleaningReport);
        }
    }
}
