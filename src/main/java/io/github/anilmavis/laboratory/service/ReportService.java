package io.github.anilmavis.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilmavis.laboratory.model.Report;
import io.github.anilmavis.laboratory.repository.ReportRepository;

@Service
public class ReportService {
    private ReportRepository repository;
    
    @Autowired
    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public List<Report> getAll() {
        return repository.findAll();
    }

    public void insert(Report report) {
        repository.save(report);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void put(Report report) {
        if (!repository.findById(report.getId()).isPresent()) {
            throw new IllegalStateException("ID does not exist");
        }
        repository.save(report);
    }
}
