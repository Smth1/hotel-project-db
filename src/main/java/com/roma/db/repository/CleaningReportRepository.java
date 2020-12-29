package com.roma.db.repository;

import com.roma.db.model.CleaningReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleaningReportRepository extends JpaRepository<CleaningReport, Integer> {
}
