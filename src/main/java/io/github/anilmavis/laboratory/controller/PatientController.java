package io.github.anilmavis.laboratory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.anilmavis.laboratory.model.Patient;
import io.github.anilmavis.laboratory.service.PatientService;

@RestController
@RequestMapping(path = "api/v1/patient")
public class PatientController {
    private PatientService service;

    @Autowired
    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Patient> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Patient insert(@RequestBody Patient patient) {
        return service.insert(patient);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void put(@RequestBody Patient patient) {
        service.put(patient);
    }
}
