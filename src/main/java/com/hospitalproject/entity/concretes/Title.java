package com.hospitalproject.entity.concretes;

import javax.persistence.*;
import java.util.List;

@Entity
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titleName;

    @OneToMany (mappedBy = "title" , orphanRemoval = true)
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "title")
    private List<MedicalCase> medicalCases;

    public Long getId() {
        return id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName.substring(0, 1).toUpperCase() + titleName.substring(1).toLowerCase().trim();
    }

    public List<MedicalCase> getMedicalCases() {
        return medicalCases;
    }

    public void setMedicalCases(List<MedicalCase> medicalCases) {
        this.medicalCases = medicalCases;
    }

    public Title() {
    }
}
