package io.github.anilmavis.laboratory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.github.anilmavis.laboratory.model.Role;
import io.github.anilmavis.laboratory.model.User;
import io.github.anilmavis.laboratory.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_existingUsername_returnsUserDetails() {
        String username = "john_doe";
        String password = "password";
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        User user = new User(username, password, roles);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_nonExistingUsername_throwsUsernameNotFoundException() {
        String username = "non_existing_user";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));

        verify(userRepository, times(1)).findByUsername(username);
    }
}
