package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends Person {

    @ManyToMany(mappedBy = "patients")
    private List<MedicalCase> medicalCases;

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;

    public List<MedicalCase> getMedicalCases() {
        return medicalCases;
    }

    public void setMedicalCases(List<MedicalCase> medicalCases) {
        this.medicalCases = medicalCases;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Patient(String isim, String soyIsim) {
        super(isim, soyIsim);
    }

    public Patient() {
    }
}
