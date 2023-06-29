package io.github.anilmavis.laboratory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.github.anilmavis.laboratory.model.User;
import io.github.anilmavis.laboratory.repository.UserRepository;

public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername(username);

        assertEquals(Optional.of(user), result);
        verify(repository, times(1)).findByUsername(username);
    }

    @Test
    public void getAll() {
        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);

        when(repository.findAll()).thenReturn(userList);

        List<User> result = userService.getAll();

        assertEquals(userList, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void insert() {
        User user = new User();
        user.setPassword("password");

        User encodedUser = new User();
        encodedUser.setPassword("encodedPassword");

        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedUser.getPassword());
        when(repository.save(user)).thenReturn(user);

        User result = userService.insert(user);

        assertEquals(user, result);
        assertEquals(encodedUser.getPassword(), result.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(repository, times(1)).save(user);
    }

    @Test
    public void delete() {
        long userId = 1L;

        userService.delete(userId);

        verify(repository, times(1)).deleteById(userId);
    }

    @Test
    public void put_existingUser() {
        User user = new User();
        user.setId(1L);
        user.setPassword("password");

        User encodedUser = new User();
        encodedUser.setId(1L);
        encodedUser.setPassword("encodedPassword");

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedUser.getPassword());
        when(repository.save(user)).thenReturn(user);

        User result = userService.put(user);

        assertEquals(user, result);
        assertEquals(encodedUser.getPassword(), result.getPassword());
        verify(repository, times(1)).findById(user.getId());
        verify(passwordEncoder, times(1)).encode("password");
        verify(repository, times(1)).save(user);
    }

    @Test
    public void put_nonExistingUser() {
        User user = new User();
        user.setId(1L);
        user.setPassword("password");

        when(repository.findById(user.getId())).thenReturn(Optional.empty());

        IllegalStateException exception = org.junit.jupiter.api.Assertions.assertThrows(
                                                                                        IllegalStateException.class,
                                                                                        () -> userService.put(user)
                                                                                        );

        assertEquals("ID does not exist", exception.getMessage());
        verify(repository, times(1)).findById(user.getId());
        verifyNoMoreInteractions(passwordEncoder, repository);
    }
}
