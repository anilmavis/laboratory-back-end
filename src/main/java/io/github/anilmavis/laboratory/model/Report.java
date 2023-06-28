package io.github.anilmavis.laboratory.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
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
    private Long id;
    
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
    private String photo;
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

    public Long getId() {
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

    public String getPhoto() {
        return photo;
    }

    public Laborant getLaborant() {
        return laborant;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setDiagnosisTitle(String diagnosisTitle) {
		this.diagnosisTitle = diagnosisTitle;
	}

	public void setDiagnosisDetail(String diagnosisDetail) {
		this.diagnosisDetail = diagnosisDetail;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setLaborant(Laborant laborant) {
		this.laborant = laborant;
	}
}
