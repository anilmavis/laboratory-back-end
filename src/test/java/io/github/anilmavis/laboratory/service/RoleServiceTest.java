package io.github.anilmavis.laboratory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.anilmavis.laboratory.model.Role;
import io.github.anilmavis.laboratory.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName_existingName_returnsOptionalRole() {
        String roleName = "ADMIN";
        Role role = new Role();
        role.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.findByName(roleName);

        assertTrue(result.isPresent());
        assertEquals(roleName, result.get().getName());
        verify(roleRepository, times(1)).findByName(roleName);
    }

    @Test
    void findByName_nonExistingName_returnsEmptyOptional() {
        String roleName = "NON_EXISTING_ROLE";

        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.findByName(roleName);

        assertFalse(result.isPresent());
        verify(roleRepository, times(1)).findByName(roleName);
    }

    @Test
    void getAll_returnsListOfRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role());
        roleList.add(new Role());

        when(roleRepository.findAll()).thenReturn(roleList);

        List<Role> result = roleService.getAll();

        assertEquals(2, result.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void insert() {
        Role role = new Role();
        role.setName("ADMIN");

        when(roleRepository.save(role)).thenReturn(role);

        Role result = roleService.insert(role);

        assertEquals(role, result);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void delete() {
        Long id = 1L;

        roleService.delete(id);

        verify(roleRepository, times(1)).deleteById(id);
    }

    @Test
    void put_existingRole_updatesRole() {
        Role role = new Role();
        role.setId(1L);

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);

        roleService.put(role);

        verify(roleRepository, times(1)).findById(role.getId());
        verify(roleRepository, times(1)).save(role);
        }

    @Test
    void put_nonExistingId_throwsIllegalStateException() {
        Role role = new Role();
        role.setId(1L);

        when(roleRepository.findById(role.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> roleService.put(role));

        verify(roleRepository, times(1)).findById(role.getId());
        verify(roleRepository, never()).save(role);
    }
}
