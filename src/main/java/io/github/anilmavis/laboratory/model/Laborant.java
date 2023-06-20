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
public class Laborant {
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
    private String hospitalId;
    @OneToMany(mappedBy = "laborant")
    private List<Report> reports;

    public Laborant(String firstName, String lastName, String hospitalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hospitalId = hospitalId;
    }

    private Laborant() {
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

    public String getHospitalId() {
        return hospitalId;
    }

    public List<Report> getReports() {
        return reports;
    }

    @Override
    public String toString() {
        return String.format("Laborant [id = %d, firstName = '%s', lastName = '%s', hospitalId = '%s']", id, firstName, lastName, hospitalId);
    }
}
