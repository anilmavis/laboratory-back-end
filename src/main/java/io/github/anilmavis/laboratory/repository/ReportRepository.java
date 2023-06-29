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
           "(:patientFirstName IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :patientFirstName, '%'))) AND " +
           "(:patientLastName IS NULL OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :patientLastName, '%'))) AND " +
           "(:tc IS NULL OR LOWER(p.tc) LIKE LOWER(CONCAT('%', :tc, '%'))) AND " +
           "(:laborantFirstName IS NULL OR LOWER(l.firstName) LIKE LOWER(CONCAT('%', :laborantFirstName, '%'))) AND " +
           "(:laborantLastName IS NULL OR LOWER(l.lastName) LIKE LOWER(CONCAT('%', :laborantLastName, '%'))) AND " +
           "(:hospitalId IS NULL OR LOWER(l.hospitalId) LIKE LOWER(CONCAT('%', :hospitalId, '%')))")
    List<Report> search(
                        String patientFirstName,
                        String patientLastName,
                        String tc,
                        String laborantFirstName,
                        String laborantLastName,
                        String hospitalId
                        );

    List<Report> findAllByOrderByDateAsc();

    List<Report> findAllByOrderByDateDesc();
}
