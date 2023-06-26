package io.github.anilmavis.laboratory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilmavis.laboratory.model.Role;
import io.github.anilmavis.laboratory.repository.RoleRepository;

@Service
public class RoleService {
    private RoleRepository repository;
    
    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Role> getAll() {
        return repository.findAll();
    }

    public Role insert(Role role) {
        if (repository.findByName(role.getName()).isPresent()) {
            throw new IllegalStateException("Name already exists");
        }
        return repository.save(role);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void put(Role role) {
        if (!repository.findById(role.getId()).isPresent()) {
            throw new IllegalStateException("ID does not exist");
        }
        repository.save(role);
    }    
}
