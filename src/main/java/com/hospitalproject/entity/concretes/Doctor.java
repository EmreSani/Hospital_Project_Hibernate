package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;


@Entity
public class Doctor extends Person {

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Title title;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalCase> medicalCases;

    @ManyToMany
    private List<Patient> patients;

    public Title getUnvan() {
        return title;
    }

    public void setUnvan(Title title) {
        this.title = title;
    }

    public List<MedicalCase> getMedicalCases() {
        return medicalCases;
    }

    public void setMedicalCases(List<MedicalCase> medicalCases) {
        this.medicalCases = medicalCases;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public Doctor() {
    }
}
