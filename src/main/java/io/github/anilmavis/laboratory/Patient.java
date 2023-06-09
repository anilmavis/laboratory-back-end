package io.github.anilmavis.laboratory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Patient {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String tc;

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

    @Override
    public String toString() {
        return String.format("Patient [id = %d, firstName = '%s', lastName = '%s', tc = '%s']", id, firstName, lastName, tc);
    }
}
