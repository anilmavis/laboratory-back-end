package io.github.anilmavis.laboratory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.anilmavis.laboratory.model.Patient;
import io.github.anilmavis.laboratory.repository.PatientRepository;

// The TC number is generated randomly.
@ExtendWith(SpringExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository repository;
    @InjectMocks
    private PatientService service;

    @BeforeEach
    void init() {
        service = new PatientService(repository);
    }

    @Test
    void testGetAll() {
        // prepare test data
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("John", "Doe", "61104071808"));
        patients.add(new Patient("Jane", "Smith", "44969282510"));
        // mock the repository behavior
        when(repository.findAll()).thenReturn(patients);
        // invoke the service method
        List<Patient> result = service.getAll();
        // verify the result
        assertEquals(patients, result);
    }

    @Test
    void testInsert() {
        Patient patient = new Patient("John", "Doe", "61104071808");
        when(repository.findByTc(patient.getTc())).thenReturn(Optional.empty());
        when(repository.save(patient)).thenReturn(patient);
        Patient result = service.insert(patient);
        assertEquals(patient, result);
    }
    
    // it should not be allowed to insert a patient with a TC that belongs to another patient
    @Test
    void testInsert_WithExistingTc() {
        Patient patient = new Patient("John", "Doe", "61104071808");
        when(repository.findByTc(patient.getTc())).thenReturn(Optional.of(patient));
        // invoke and verify that an IllegalStateException is thrown
        assertThrows(IllegalStateException.class, () -> service.insert(patient));
    }

    @Test
    void testDelete() {
        long patientId = 1;
        service.delete(patientId);
        // verify that the repository deleteById method is called with the correct ID
        verify(repository).deleteById(patientId);
    }

    @Test
    void testPut() {
        Patient patient = new Patient("John", "Doe", "61104071808");
        when(repository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(repository.save(patient)).thenReturn(patient);
        service.put(patient);
        verify(repository).save(patient);
    }

    // it should not be allowed to modify a patient with nonexistent ID
    @Test
    void testPut_WithNonExistingId() {
        Patient patient = new Patient("John", "Doe", "61104071808");
        when(repository.findById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> service.put(patient));
    }
}
