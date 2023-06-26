package io.github.anilmavis.laboratory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.anilmavis.laboratory.model.User;
import io.github.anilmavis.laboratory.repository.UserRepository;

@Service
public class UserService {
    private UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User insert(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already exists");
        }
        return repository.save(user);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void put(User user) {
        if (!repository.findById(user.getId()).isPresent()) {
            throw new IllegalStateException("ID does not exist");
        }
        repository.save(user);
    }
}
