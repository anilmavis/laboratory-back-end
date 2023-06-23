package io.github.anilmavis.laboratory.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Report {
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @NotNull
    @NotEmpty
    private String diagnosisTitle;
    @NotNull
    @NotEmpty
    private String diagnosisDetail;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date date;
    @Lob
    private String photoPath;
    
    @ManyToOne
    @JoinColumn(name = "laborant_id")
    private Laborant laborant;

    public Report(Patient patient, String diagnosisTitle, String diagnosisDetail,  Laborant laborant) {
        this.patient = patient;
        this.diagnosisTitle = diagnosisTitle;
        this.diagnosisDetail = diagnosisDetail;
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
}
