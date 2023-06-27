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

    public Patient insert(Patient patient) {
        if (repository.findByTc(patient.getTc()).isPresent()) {
            throw new IllegalStateException("TC already exists");
        }

        if (!checkTc(patient.getTc())) {
            throw new IllegalArgumentException("Invalid Turkey identity number");
        }
        return repository.save(patient);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void put(Patient patient) {
        if (!repository.findById(patient.getId()).isPresent()) {
            throw new IllegalStateException("ID does not exist");
        }

        if (!checkTc(patient.getTc())) {
            throw new IllegalArgumentException("Invalid Turkey identity number");
        }
        repository.save(patient);
    }

    private boolean checkTc(String tc) {
        // for format
        if(tc.startsWith("0") || !tc.matches("[0-9]{11}")){
            return false;
        }

        // for validity
        int [] array = new int [11];
        for (int i = 0; i < 11; i++){
            array[i] = Character.getNumericValue(tc.toCharArray()[i]);
        }
        int n1 = 0;
        int n2 = array[9];

        for(int j = 0; j < 9; j++){
            if(j % 2 == 0) {
                n1 += array[j] * 7;
            } else {
                n1 -=  array[j];
            }
            n2 += array[j];
        }
        boolean isValid = array[9] == n1 % 10 && array[10] == n2 % 10;
        return isValid;
    }
}
