package com.project.reportmanager.repository;

import com.project.reportmanager.model.Report;
import org.springframework.data.repository.CrudRepository;

// Spring Data JPA bu interface'i otomatik olarak algılayıp içini doldurur
public interface ReportRepository extends CrudRepository<Report, Long> {
}