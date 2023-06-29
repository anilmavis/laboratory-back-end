package io.github.anilmavis.laboratory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        MockitoAnnotations.openMocks(this);
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
        verify(repository, times(1)).findAll();
    }

    @Test
    void testInsert() {
        Laborant laborant = new Laborant("John", "Doe", "6110407");
        when(repository.save(laborant)).thenReturn(laborant);
        Laborant result = service.insert(laborant);
        assertEquals(laborant, result);
        verify(repository, times(1)).save(laborant);
    }

    @Test
    void delete() {
        long laborantId = 1;
        service.delete(laborantId);
        // verify that the repository deleteById method is called with the correct ID
        verify(repository).deleteById(laborantId);
    }

    @Test
    void put() {
        Laborant laborant = new Laborant("John", "Doe", "6110407");
        when(repository.findById(laborant.getId())).thenReturn(Optional.of(laborant));
        when(repository.save(laborant)).thenReturn(laborant);
        service.put(laborant);
        verify(repository).save(laborant);
    }

    // it should not be allowed to modify a laborant with nonexistent ID
    @Test
    void put_WithNonExistingId() {
        Laborant laborant = new Laborant("John", "Doe", "6110407");
        when(repository.findById(laborant.getId())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> service.put(laborant));
    }
}
