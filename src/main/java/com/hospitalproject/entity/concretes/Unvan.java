package com.hospitalproject.entity.concretes;

import javax.persistence.*;
import java.util.List;

@Entity
public class Unvan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String unvan;

    @OneToMany (mappedBy = "unvan")
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "unvan")
    private List<MedicalCase> medicalCases;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan.substring(0, 1).toUpperCase() + unvan.substring(1).toLowerCase().trim();
    }

    public List<MedicalCase> getMedicalCases() {
        return medicalCases;
    }

    public void setMedicalCases(List<MedicalCase> medicalCases) {
        this.medicalCases = medicalCases;
    }

    public Unvan() {
    }
}
