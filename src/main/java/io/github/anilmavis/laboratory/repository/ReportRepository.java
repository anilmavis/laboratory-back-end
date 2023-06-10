package io.github.anilmavis.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.anilmavis.laboratory.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {}
