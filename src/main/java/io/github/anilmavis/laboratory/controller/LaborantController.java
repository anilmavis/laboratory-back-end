package io.github.anilmavis.laboratory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.anilmavis.laboratory.model.Laborant;
import io.github.anilmavis.laboratory.service.LaborantService;

@RestController
@RequestMapping(path = "api/v1/laborant")
public class LaborantController {
    private LaborantService service;

    @Autowired
    public LaborantController(LaborantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Laborant> getAll() {
        return service.getAll();
    }

    @PostMapping
    public void insert(@RequestBody Laborant laborant) {
        service.insert(laborant);
    }
}
