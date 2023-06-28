package io.github.anilmavis.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilmavis.laboratory.model.Laborant;
import io.github.anilmavis.laboratory.model.Patient;
import io.github.anilmavis.laboratory.model.Report;
import io.github.anilmavis.laboratory.repository.LaborantRepository;
import io.github.anilmavis.laboratory.repository.PatientRepository;
import io.github.anilmavis.laboratory.repository.ReportRepository;

@Service
public class ReportService {
    private ReportRepository reportRepository;
    private PatientRepository patientRepository;
    private LaborantRepository laborantRepository;
    
    @Autowired
    public ReportService(ReportRepository reportRepository, PatientRepository patientRepository, LaborantRepository laborantRepository) {
        this.reportRepository = reportRepository;
        this.patientRepository = patientRepository;
        this.laborantRepository = laborantRepository;
    }

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Report insert(Report report) {
        Patient patient = patientRepository.findById(report.getPatient().getId()).get();
        Laborant laborant = laborantRepository.findById(report.getLaborant().getId()).get();
        report.setPatient(patient);
        report.setLaborant(laborant);
        return reportRepository.save(report);
    }

    public void delete(long id) {
        reportRepository.deleteById(id);
    }

    public void put(Report report) {
        if (!reportRepository.findById(report.getId()).isPresent()) {
            throw new IllegalStateException("ID does not exist");
        }
        reportRepository.save(report);
    }

    public List<Report> findAll(String patientFirstName,
                                String patientLastName,
                                String tc,
                                String laborantFirstName,
                                String laborantLastName,
                                String hospitalId) {
        return reportRepository.search(patientFirstName,
                                       patientLastName,
                                       tc,
                                       laborantFirstName,
                                       laborantLastName,
                                       hospitalId);
    }

    public List<Report> sortByDateAsc() {
        return reportRepository.findAllByOrderByDateAsc();
    }

    public List<Report> sortByDateDesc() {
        return reportRepository.findAllByOrderByDateDesc();
    }
}
