package io.github.anilmavis.laboratory.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.anilmavis.laboratory.model.Role;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTest {
    @Autowired
    RoleRepository repository;

    @Test
    public void getAll() {
        repository.deleteAll();
        
        repository.save(new Role("USER"));
        repository.save(new Role("ADMIN"));
        List<Role> list = repository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.get(0).getId()).isNotNegative();
        Assertions.assertThat(list.get(1).getId()).isNotNegative();
        Assertions.assertThat(list.get(0).getName()).isNotEmpty();
        Assertions.assertThat(list.get(0).getName()).isNotNull();
        Assertions.assertThat(list.get(1).getName()).isNotEmpty();
        Assertions.assertThat(list.get(1).getName()).isNotNull();
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
        Role saved = new Role("USER");
        Role inserted = repository.save(saved);
        Assertions.assertThat(inserted).isNotNull();
        Assertions.assertThat(inserted.getName()).isNotEmpty();
        Assertions.assertThat(inserted.getName()).isEqualTo(saved.getName());
        Assertions.assertThat(inserted.getId()).isNotNegative();
        repository.deleteAll();
    }

    @Test
    public void delete() {
        Role inserted = repository.save(new Role("USER"));
        repository.delete(inserted);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
                repository.findById(inserted.getId()).get();
            });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
    }
}
