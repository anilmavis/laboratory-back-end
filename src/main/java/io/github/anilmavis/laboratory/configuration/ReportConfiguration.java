package io.github.anilmavis.laboratory.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.anilmavis.laboratory.model.Laborant;
import io.github.anilmavis.laboratory.model.Patient;
import io.github.anilmavis.laboratory.model.Report;
import io.github.anilmavis.laboratory.repository.LaborantRepository;
import io.github.anilmavis.laboratory.repository.PatientRepository;
import io.github.anilmavis.laboratory.repository.ReportRepository;;

@Configuration
public class ReportConfiguration {
	@Bean
	public CommandLineRunner populateReports(ReportRepository repository, PatientRepository patientRepository, LaborantRepository laborantRepository) throws Exception {
        return (args) -> {
            Patient patient = patientRepository.save(new Patient("Benjamin", "Davis", "56149779840"));
            Laborant laborant = laborantRepository.save(new Laborant("Sophia", "Rodriguez", "2202690"));
            repository.save(new Report(patient, "Generalised Anxiety Disorder", "A mental health condition characterized by excessive and persistent worry or anxiety.", laborant));
        };
	}
}
