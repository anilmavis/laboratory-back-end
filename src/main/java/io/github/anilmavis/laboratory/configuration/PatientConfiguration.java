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
	public CommandLineRunner demo(PatientRepository repository) throws Exception {
        return (args) -> {
            List<Patient> patients = List.of(new Patient("socrates", "athens", "55555555555"),
                                             new Patient("plato", "lina", "55555555554"),
                                             new Patient("lorem", "ipsum", "55555555533"));
            repository.saveAll(patients);
        };
	}
}
