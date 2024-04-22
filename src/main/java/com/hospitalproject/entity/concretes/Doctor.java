package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;


@Entity
public class Doctor extends Person {

    @ManyToOne
    private Unvan unvan;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalCase> medicalCases;

    @ManyToMany
    private List<Patient> patients;

    public Unvan getUnvan() {
        return unvan;
    }

    public void setUnvan(Unvan unvan) {
        this.unvan = unvan;
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
