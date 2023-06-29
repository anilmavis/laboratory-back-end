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
import io.github.anilmavis.laboratory.model.User;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    private Set<Role> roles = Set.of(new Role("USER"));

    @Test
    public void getAll() {
        repository.deleteAll();
        
        repository.save(new User("sypha", "password", roles));
        repository.save(new User("rimuru", "password", roles));
        List<User> list = repository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.get(0).getId()).isNotNegative();
        Assertions.assertThat(list.get(1).getId()).isNotNegative();
        Assertions.assertThat(list.get(0).getUsername()).isEqualTo("sypha");
        Assertions.assertThat(list.get(1).getUsername()).isEqualTo("rimuru");
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
        User saved = new User("francis", "password", roles);
        User inserted = repository.save(saved);
        Assertions.assertThat(inserted).isNotNull();
        Assertions.assertThat(inserted.getUsername()).isNotEmpty();
        Assertions.assertThat(inserted.getUsername()).isEqualTo(saved.getUsername());
        Assertions.assertThat(inserted.getId()).isNotNegative();
        repository.deleteAll();
    }

    @Test
    public void delete() {
        User inserted = repository.save(new User("francis", "password", roles));
        repository.delete(inserted);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
                repository.findById(inserted.getId()).get();
            });
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
    }
}
