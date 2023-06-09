package io.github.anilmavis.laboratory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Laborant {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String hospitalId;

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

    @Override
    public String toString() {
        return String.format("Laborant [id = %d, firstName = '%s', lastName = '%s', hospitalId = '%s']", id, firstName, lastName, hospitalId);
    }
}
