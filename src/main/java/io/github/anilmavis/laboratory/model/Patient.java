package io.github.anilmavis.laboratory.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Patient {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String tc;
    @OneToMany(mappedBy = "patient")
    private List<Report> reports;

    public Patient(String firstName, String lastName, String tc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tc = tc;
    }

    private Patient() {
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTc() {
        return tc;
    }

    public List<Report> getReports() {
        return reports;
    }

    @Override
    public String toString() {
        return String.format("Patient [id = %d, firstName = '%s', lastName = '%s', tc = '%s']", id, firstName, lastName, tc);
    }
}
