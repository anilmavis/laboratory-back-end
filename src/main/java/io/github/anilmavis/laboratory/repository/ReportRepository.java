package io.github.anilmavis.laboratory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.anilmavis.laboratory.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM Report r " +
           "LEFT JOIN r.patient p " +
           "LEFT JOIN r.laborant l " +
           "WHERE " +
           "(:patientFirstName IS NULL OR LOWER(p.firstName) = LOWER(:patientFirstName)) AND " +
           "(:patientLastName IS NULL OR LOWER(p.lastName) = LOWER(:patientLastName)) AND " +
           "(:tc IS NULL OR LOWER(p.tc) = LOWER(:tc)) AND " +
           "(:laborantFirstName IS NULL OR LOWER(l.firstName) = LOWER(:laborantFirstName)) AND " +
           "(:laborantLastName IS NULL OR LOWER(l.lastName) = LOWER(:laborantLastName)) AND " +
           "(:hospitalId IS NULL OR l.hospitalId = :hospitalId)")
    List<Report> search(
                        String patientFirstName,
                        String patientLastName,
                        String tc,
                        String laborantFirstName,
                        String laborantLastName,
                        String hospitalId
                        );
}
