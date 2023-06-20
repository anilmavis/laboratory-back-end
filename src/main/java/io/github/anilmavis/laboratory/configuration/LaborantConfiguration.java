package io.github.anilmavis.laboratory.configuration;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.anilmavis.laboratory.model.Laborant;
import io.github.anilmavis.laboratory.repository.LaborantRepository;

@Configuration
public class LaborantConfiguration {
	@Bean
	public CommandLineRunner poopulateLaborants(LaborantRepository repository) throws Exception {
        return (args) -> {
            List<Laborant> laborants = List.of(new Laborant("Laborant", "1", "1234567"),
                                             new Laborant("Laborant", "2", "1234568"),
                                             new Laborant("Laborant", "3", "1234569"));
            repository.saveAll(laborants);
        };
	}
}
