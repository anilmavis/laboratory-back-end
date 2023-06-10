package io.github.anilmavis.laboratory.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Patient {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String tc;
    @OneToMany
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
