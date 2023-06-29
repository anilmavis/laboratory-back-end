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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.anilmavis.laboratory.model.Report;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReportRepositoryTest {
    @Autowired
    ReportRepository repository;

    @Test
    public void getAll() {
        repository.deleteAll();
        repository.save(new Report(null, "diagnosis 1", "diagnosis details", null));
        repository.save(new Report(null, "diagnosis 2", "diagnosis details", null));
        List<Report> list = repository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.get(0).getId()).isNotNegative();
        Assertions.assertThat(list.get(1).getId()).isNotNegative();
        Assertions.assertThat(list.get(0).getDiagnosisTitle()).isEqualTo("diagnosis 1");
        Assertions.assertThat(list.get(1).getDiagnosisTitle()).isEqualTo("diagnosis 2");
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
        Report saved = repository.save(new Report(null, "diagnosis 1", "diagnosis details", null));
        Report inserted = repository.save(saved);
        Assertions.assertThat(inserted).isNotNull();
        Assertions.assertThat(inserted.getDiagnosisTitle()).isNotEmpty();
        Assertions.assertThat(inserted.getDiagnosisTitle()).isEqualTo(saved.getDiagnosisTitle());
        Assertions.assertThat(inserted.getId()).isNotNegative();
        repository.deleteAll();
    }

    @Test
    public void delete() {
        Report inserted = repository.save(new Report(null, "diagnosis 1", "diagnosis details", null));
        repository.delete(inserted);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
                repository.findById(inserted.getId()).get();
            });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
    }
}
