package io.github.anilmavis.laboratory.configuration;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.anilmavis.laboratory.model.Patient;
import io.github.anilmavis.laboratory.repository.PatientRepository;

@Configuration
public class PatientConfiguration {
    @Bean
    public CommandLineRunner populatePatients(PatientRepository repository) throws Exception {
        return (args) -> {
            List<Patient> patients = List.of(new Patient("John", "Doe", "61104071808"),
                                             new Patient("Jane", "Smith", "44969282510"));
            repository.saveAll(patients);
        };
    }
}
