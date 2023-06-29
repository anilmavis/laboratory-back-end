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

import io.github.anilmavis.laboratory.model.Laborant;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LaborantRepositoryTest {
    @Autowired
    LaborantRepository repository;

    @Test
    public void getAll() {
        repository.deleteAll();
        repository.save(new Laborant("sypha", "jane", "6110407"));
        repository.save(new Laborant("rimuru", "jones", "4496928"));
        List<Laborant> list = repository.findAll();
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
        Laborant saved = new Laborant("francis", "sky", "4487408");
        Laborant inserted = repository.save(saved);
        Assertions.assertThat(inserted).isNotNull();
        Assertions.assertThat(inserted.getFirstName()).isNotEmpty();
        Assertions.assertThat(inserted.getFirstName()).isEqualTo(saved.getFirstName());
        Assertions.assertThat(inserted.getId()).isNotNegative();
        repository.deleteAll();
    }

    @Test
    public void insert_withSameHospitalId() {
        repository.deleteAll();
        repository.save(new Laborant("sypha", "jane", "6110407"));
        Laborant saved = new Laborant("amie", "green", "6110407"); // same hospital ID with sypha
        Exception e = assertThrows(DataIntegrityViolationException.class, () -> {
                repository.saveAndFlush(saved);
            });
        Assertions.assertThat(e).isNotNull();
        Assertions.assertThat(e.getClass()).isEqualTo(DataIntegrityViolationException.class);
    }

    @Test
    public void delete() {
        Laborant inserted = repository.save(new Laborant("francis", "sky", "44874080522"));
        repository.delete(inserted);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
                repository.findById(inserted.getId()).get();
            });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
    }
}
