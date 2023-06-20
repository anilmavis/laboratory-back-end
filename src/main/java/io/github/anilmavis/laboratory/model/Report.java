package io.github.anilmavis.laboratory.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Report {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;
    @NotNull
    @NotEmpty
    String diagnosisTitle;
    @NotNull
    @NotEmpty
    String diagnosisDetail;
    @CreatedDate
    Date date;
    @NotNull
    @NotEmpty
    String photoPath;
    @ManyToOne
    @JoinColumn(name = "laborant_id")
    Laborant laborant;

    public Report(Patient patient, String diagnosisTitle, String diagnosisDetail, Date date, String photoPath, Laborant laborant) {
        this.patient = patient;
        this.diagnosisTitle = diagnosisTitle;
        this.diagnosisDetail = diagnosisDetail;
        this.date = date;
        this.photoPath = photoPath;
        this.laborant = laborant;
    }

    private Report() {
    }

    public long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDiagnosisTitle() {
        return diagnosisTitle;
    }

    public String getDiagnosisDetail() {
        return diagnosisDetail;
    }

    public Date getDate() {
        return date;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Laborant getLaborant() {
        return laborant;
    }

    @Override
    public String toString() {
        return String.format("Report [id = %d, patient = %s, diagnosisTitle = '%s', diagnosisDetail = '%s', date = '%s', photoPath = '%s', laborant = %s]", id, patient, diagnosisTitle, diagnosisDetail, date, photoPath, laborant);
    }
}
