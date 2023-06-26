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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.anilmavis.laboratory.model.Report;
import io.github.anilmavis.laboratory.service.ReportService;

@RestController
@RequestMapping(path = "api/v1/report")
public class ReportController {
    private ReportService service;

    @Autowired
    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping
    public List<Report> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Report insert(@RequestBody Report report) {
        return service.insert(report);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public void put(@RequestBody Report report) {
        service.put(report);
    }

    @GetMapping("search")
    public List<Report> searchReports(
                                      @RequestParam(required = false) String patientFirstName,
                                      @RequestParam(required = false) String patientLastName,
                                      @RequestParam(required = false) String tc,
                                      @RequestParam(required = false) String laborantFirstName,
                                      @RequestParam(required = false) String laborantLastName,
                                      @RequestParam(required = false) String hospitalId
                                      ) {
        return service.findAll(
                               patientFirstName, patientLastName, tc,
                               laborantFirstName, laborantLastName, hospitalId
                               );
    }
}
