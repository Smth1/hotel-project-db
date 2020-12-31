package com.roma.db.service;

import com.roma.db.model.CleaningReport;
import com.roma.db.repository.CleaningReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CleaningReportService {
    @Autowired
    private CleaningReportRepository cleaningReportRepository;

    public void makeCleaningReport(CleaningReport cleaningReport) {
        cleaningReportRepository.save(cleaningReport);
    }
}
