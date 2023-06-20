package io.github.anilmavis.laboratory.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.anilmavis.laboratory.repository.ReportRepository;;

@Configuration
public class ReportConfiguration {
	@Bean
	public CommandLineRunner populateReports(ReportRepository repository) throws Exception {
        return (args) -> {
        };
	}
}
