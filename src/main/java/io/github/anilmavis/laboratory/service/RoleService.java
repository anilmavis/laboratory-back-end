package io.github.anilmavis.laboratory.service;

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
}
