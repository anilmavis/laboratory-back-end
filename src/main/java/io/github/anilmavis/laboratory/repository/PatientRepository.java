package io.github.anilmavis.laboratory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.anilmavis.laboratory.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByFirstName(String firstName);
    List<Patient> findByLastName(String lastName);
    Optional<Patient> findByTc(String tc);
}
