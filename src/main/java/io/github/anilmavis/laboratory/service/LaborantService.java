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

    public Laborant insert(Laborant laborant) {

        if (!checkId(laborant.getHospitalId())) {
            throw new IllegalArgumentException("Invalid hospital ID");
        }
        return repository.save(laborant);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void put(Laborant laborant) {
        if (!repository.findById(laborant.getId()).isPresent()) {
            throw new IllegalStateException("ID does not exist");
        }

        if (!checkId(laborant.getHospitalId())) {
            throw new IllegalArgumentException("Invalid hospital ID");
        }
        repository.save(laborant);
    }

    public boolean checkId(String id) {
        return id.matches("[0-9]{7}");
    }
}
