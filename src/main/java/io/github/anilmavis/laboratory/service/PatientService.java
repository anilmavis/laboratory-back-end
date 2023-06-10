package io.github.anilmavis.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilmavis.laboratory.model.Patient;
import io.github.anilmavis.laboratory.repository.PatientRepository;

@Service
public class PatientService {
    private PatientRepository repository;
    
    @Autowired
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAll() {
        return repository.findAll();
    }

    public void insert(Patient patient) {
        if (repository.findByTc(patient.getTc()).isPresent()) {
            throw new IllegalStateException("TC already exists");
        }
        repository.save(patient);
    }
}
