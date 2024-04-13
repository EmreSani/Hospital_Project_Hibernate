package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends Person {


    @ManyToOne
    private Doctor doctor;

    @ManyToMany(mappedBy = "patients")
    private List<MedicalCase> medicalCases;

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;

    public Patient(String isim, String soyIsim, List<Doctor> doctors, List<MedicalCase> medicalCases) {
        super(isim, soyIsim);
        this.doctors = doctors;
        this.medicalCases = medicalCases;
    }

    public Patient(List<Doctor> doctors, List<MedicalCase> medicalCases) {
        this.doctors = doctors;
        this.medicalCases = medicalCases;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<MedicalCase> getMedicalCases() {
        return medicalCases;
    }

    public void setMedicalCases(List<MedicalCase> medicalCases) {
        this.medicalCases = medicalCases;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "doctors=" + doctors +
                ", medicalCases=" + medicalCases +
                ", id=" + id +
                ", isim='" + isim + '\'' +
                ", soyIsim='" + soyIsim + '\'' +
                '}';
    }
}
