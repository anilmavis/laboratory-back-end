package io.github.anilmavis.laboratory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Laborant insert(@RequestBody Laborant laborant) {
        return service.insert(laborant);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PutMapping
    public void put(@RequestBody Laborant laborant) {
        service.put(laborant);
    }
}
