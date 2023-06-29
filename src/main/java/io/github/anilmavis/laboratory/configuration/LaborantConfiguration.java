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
            List<Laborant> laborants = List.of(new Laborant("Sypha", "Green", "6110407"),
                                               new Laborant("Lewis", "Tempest", "4496928"));
            repository.saveAll(laborants);
        };
    }
}
