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

import io.github.anilmavis.laboratory.model.Laborant;
import io.github.anilmavis.laboratory.repository.LaborantRepository;

@ExtendWith(SpringExtension.class)
public class LaborantServiceTest {
    @Mock
    private LaborantRepository repository;
    @InjectMocks
    private LaborantService service;

    @BeforeEach
    void init() {
        service = new LaborantService(repository);
    }

    @Test
    void testGetAll() {
        // prepare test data
        List<Laborant> laborants = new ArrayList<>();
        laborants.add(new Laborant("John", "Doe", "6110407"));
        laborants.add(new Laborant("Jane", "Smith", "4496928"));
        // mock the repository behavior
        when(repository.findAll()).thenReturn(laborants);
        // invoke the service method
        List<Laborant> result = service.getAll();
        // verify the result
        assertEquals(laborants, result);
    }

    @Test
    void testInsert() {
        Laborant laborant = new Laborant("John", "Doe", "6110407");
        when(repository.findByHospitalId(laborant.getHospitalId())).thenReturn(Optional.empty());
        when(repository.save(laborant)).thenReturn(laborant);
        Laborant result = service.insert(laborant);
        assertEquals(laborant, result);
    }
    
    // it should not be allowed to insert a laborant with a hospital ID that belongs to another laborant
    @Test
    void testInsert_WithExistingHospitalId() {
        Laborant laborant = new Laborant("John", "Doe", "6110407");
        when(repository.findByHospitalId(laborant.getHospitalId())).thenReturn(Optional.of(laborant));
        // invoke and verify that an IllegalStateException is thrown
        assertThrows(IllegalStateException.class, () -> service.insert(laborant));
    }

    @Test
    void testDelete() {
        long laborantId = 1;
        service.delete(laborantId);
        // verify that the repository deleteById method is called with the correct ID
        verify(repository).deleteById(laborantId);
    }

    @Test
    void testPut() {
        Laborant laborant = new Laborant("John", "Doe", "6110407");
        when(repository.findById(laborant.getId())).thenReturn(Optional.of(laborant));
        when(repository.save(laborant)).thenReturn(laborant);
        service.put(laborant);
        verify(repository).save(laborant);
    }

    // it should not be allowed to modify a laborant with nonexistent ID
    @Test
    void testPut_WithNonExistingId() {
        Laborant laborant = new Laborant("John", "Doe", "6110407");
        when(repository.findById(laborant.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> service.put(laborant));
    }
}
