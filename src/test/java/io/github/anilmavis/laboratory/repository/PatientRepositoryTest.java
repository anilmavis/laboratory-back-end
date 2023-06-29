package io.github.anilmavis.laboratory.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.anilmavis.laboratory.model.Patient;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PatientRepositoryTest {
    @Autowired
    PatientRepository repository;

    @Test
    public void getAll() {
        repository.deleteAll();
        repository.save(new Patient("sypha", "jane", "61104071808"));
        repository.save(new Patient("rimuru", "jones", "44969282510"));
        List<Patient> list = repository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.get(0).getId()).isNotNegative();
        Assertions.assertThat(list.get(1).getId()).isNotNegative();
        Assertions.assertThat(list.get(0).getFirstName()).isEqualTo("sypha");
        Assertions.assertThat(list.get(1).getFirstName()).isEqualTo("rimuru");
        repository.deleteAll();
    }

    @Test
    public void getInvalid() {
        repository.deleteAll();
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
                repository.findById(100L).get();
            });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
        repository.deleteAll();
    }

    @Test
    public void insert() {
        repository.deleteAll();
        Patient saved = new Patient("francis", "sky", "44874080522");
        Patient inserted = repository.save(saved);
        Assertions.assertThat(inserted).isNotNull();
        Assertions.assertThat(inserted.getFirstName()).isNotEmpty();
        Assertions.assertThat(inserted.getFirstName()).isEqualTo(saved.getFirstName());
        Assertions.assertThat(inserted.getId()).isNotNegative();
        repository.deleteAll();
    }

    @Test
    public void insert_withSameTc() {
        repository.deleteAll();
        repository.save(new Patient("sypha", "jane", "61104071808"));
        Patient saved = new Patient("amie", "green", "61104071808"); // same TC with sypha
        Exception e = assertThrows(DataIntegrityViolationException.class, () -> {
                repository.saveAndFlush(saved);
            });
        Assertions.assertThat(e).isNotNull();
        Assertions.assertThat(e.getClass()).isEqualTo(DataIntegrityViolationException.class);
    }

    @Test
    public void delete() {
        Patient inserted = repository.save(new Patient("francis", "sky", "44874080522"));
        repository.delete(inserted);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
                repository.findById(inserted.getId()).get();
            });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
    }
}
