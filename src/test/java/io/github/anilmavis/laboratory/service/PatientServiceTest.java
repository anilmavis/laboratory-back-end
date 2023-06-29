package io.github.anilmavis.laboratory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_returnsListOfPatients() {
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
    void insert() {
        Patient patient = new Patient("John", "Doe", "61104071808");
        when(repository.findByTc(patient.getTc())).thenReturn(Optional.empty());
        when(repository.save(patient)).thenReturn(patient);
        Patient result = service.insert(patient);
        assertEquals(patient, result);
    }

    @Test
    void delete() {
        long patientId = 1;
        service.delete(patientId);
        verify(repository).deleteById(patientId);
    }

    @Test
    void put() {
        Patient patient = new Patient("John", "Doe", "61104071808");
        when(repository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(repository.save(patient)).thenReturn(patient);
        service.put(patient);
        verify(repository).save(patient);
    }

    // it should not be allowed to modify a patient with nonexistent ID
    @Test
    void put_WithNonExistingId() {
        Patient patient = new Patient("John", "Doe", "61104071808");
        when(repository.findById(patient.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> service.put(patient));
    }

    @Test
    public void checkTc_withInvalidId() {
        String invalid = "12345678902";
        
        boolean result = service.checkTc(invalid);

        assertFalse(result);
    }

    @Test
    public void checkTc_withValidTc() {
        String valid = "61104071808";

        boolean result = service.checkTc(valid);

        assertTrue(result);
    }
}
