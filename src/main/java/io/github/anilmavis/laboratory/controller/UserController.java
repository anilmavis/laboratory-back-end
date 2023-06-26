package io.github.anilmavis.laboratory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.anilmavis.laboratory.model.User;
import io.github.anilmavis.laboratory.service.UserService;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public User insert(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities()
    .stream()
    .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        System.out.println(isAdmin);
        return service.insert(user);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void put(@RequestBody User user) {
        service.put(user);
    }
}
