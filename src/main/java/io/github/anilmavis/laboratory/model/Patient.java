package io.github.anilmavis.laboratory.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long id;
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
    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    private List<Report> reports;

    public Patient(String firstName, String lastName, String tc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tc = tc;
    }

    private Patient() {
    }

    public Long getId() {
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTc(String tc) {
		this.tc = tc;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
}
