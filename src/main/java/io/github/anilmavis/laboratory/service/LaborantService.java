package io.github.anilmavis.laboratory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilmavis.laboratory.model.Laborant;
import io.github.anilmavis.laboratory.repository.LaborantRepository;

@Service
public class LaborantService {
    private LaborantRepository repository;
    
    @Autowired
    public LaborantService(LaborantRepository repository) {
        this.repository = repository;
    }

    public List<Laborant> getAll() {
        return repository.findAll();
    }

    public void insert(Laborant laborant) {
        if (repository.findByHospitalId(laborant.getHospitalId()).isPresent()) {
            throw new IllegalStateException("hospital ID already exists");
        }
        repository.save(laborant);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void put(Laborant laborant) {
        repository.save(laborant);
    }
}
