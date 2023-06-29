package io.github.anilmavis.laboratory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.anilmavis.laboratory.model.Laborant;
import io.github.anilmavis.laboratory.model.Patient;
import io.github.anilmavis.laboratory.model.Report;
import io.github.anilmavis.laboratory.repository.LaborantRepository;
import io.github.anilmavis.laboratory.repository.PatientRepository;
import io.github.anilmavis.laboratory.repository.ReportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private LaborantRepository laborantRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_returnsListOfReports() {
        List<Report> reportList = new ArrayList<>();
        reportList.add(new Report());
        reportList.add(new Report());

        when(reportRepository.findAll()).thenReturn(reportList);

        List<Report> result = reportService.getAll();

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void insert_validReport_returnsSavedReport() {
        Report report = new Report();
        Patient patient = new Patient();
        patient.setId(1L);
        Laborant laborant = new Laborant();
        laborant.setId(2L);
        report.setPatient(patient);
        report.setLaborant(laborant);

        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(laborantRepository.findById(laborant.getId())).thenReturn(Optional.of(laborant));
        when(reportRepository.save(report)).thenReturn(report);

        Report result = reportService.insert(report);

        assertEquals(report, result);
        verify(patientRepository, times(1)).findById(patient.getId());
        verify(laborantRepository, times(1)).findById(laborant.getId());
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    void delete() {
        long id = 1;

        reportService.delete(id);

        verify(reportRepository, times(1)).deleteById(id);
    }

    @Test
    void put_existingReport_updatesReport() {
        Report report = new Report();
        report.setId(1L);

        when(reportRepository.findById(report.getId())).thenReturn(Optional.of(report));
        when(reportRepository.save(report)).thenReturn(report);

        reportService.put(report);

        verify(reportRepository, times(1)).findById(report.getId());
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    void put_nonExistingId_throwsIllegalStateException() {
        Report report = new Report();
        report.setId(1L);

        when(reportRepository.findById(report.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> reportService.put(report));

        verify(reportRepository, times(1)).findById(report.getId());
        verify(reportRepository, never()).save(report);
    }

    @Test
    void findAll_returnsListOfReports() {
        String patientFirstName = "John";
        String patientLastName = "Doe";
        String tc = "1234567890";
        String laborantFirstName = "Jane";
        String laborantLastName = "Smith";
        String hospitalId = "H123";

        List<Report> reportList = new ArrayList<>();
        reportList.add(new Report());
        reportList.add(new Report());

        when(reportRepository.search(patientFirstName, patientLastName, tc, laborantFirstName, laborantLastName, hospitalId))
            .thenReturn(reportList);

        List<Report> result = reportService.findAll(patientFirstName, patientLastName, tc, laborantFirstName, laborantLastName, hospitalId);

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).search(patientFirstName, patientLastName, tc, laborantFirstName, laborantLastName, hospitalId);
    }

    @Test
    void sortByDateAsc_returnsListOfReportsSortedByDateAscending() {
        Report report1 = new Report();
        Date date1 = new Date(2023, 6, 29);
        report1.setDate(date1);

        Report report2 = new Report();
        Date date2 = new Date(2023, 6, 30);
        report2.setDate(date2);


        List<Report> reportList = Arrays.asList(report1, report2);
        when(reportRepository.findAllByOrderByDateAsc()).thenReturn(reportList);

        List<Report> result = reportService.sortByDateAsc();


        assertEquals(report1, result.get(0));
        assertEquals(report2, result.get(1));


        verify(reportRepository, times(1)).findAllByOrderByDateAsc();
    }

    @Test
    void sortByDateDesc_returnsListOfReportsSortedByDateDescending() {

        Report report1 = new Report();
        Date date1 = new Date(2023, 6, 29);
        report1.setDate(date1);

        Report report2 = new Report();
        Date date2 = new Date(2023, 6, 30);
        report2.setDate(date2);


        List<Report> reportList = Arrays.asList(report2, report1);
        when(reportRepository.findAllByOrderByDateDesc()).thenReturn(reportList);


        List<Report> result = reportService.sortByDateDesc();


        assertEquals(report2, result.get(0));
        assertEquals(report1, result.get(1));


        verify(reportRepository, times(1)).findAllByOrderByDateDesc();
    }
}
