package io.github.anilmavis.laboratory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.anilmavis.laboratory.model.Laborant;

@Repository
public interface LaborantRepository extends JpaRepository<Laborant, Long> {
    List<Laborant> findByFirstName(String firstName);
    List<Laborant> findByLastName(String lastName);
    Optional<Laborant> findByHospitalId(String hospitalId);
}
