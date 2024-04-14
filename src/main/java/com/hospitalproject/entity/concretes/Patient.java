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

    public Patient() {
    }

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

    public Patient(String isim, String soyIsim, List<MedicalCase> medicalCases, List<Doctor> doctors) {
        super(isim, soyIsim);
        this.medicalCases = medicalCases;
        this.doctors = doctors;
    }

    public Patient(List<MedicalCase> medicalCases, List<Doctor> doctors) {
        this.medicalCases = medicalCases;
        this.doctors = doctors;
    }

    public Patient(String isim, String soyIsim, List<MedicalCase> medicalCases) {
        super(isim, soyIsim);
        this.medicalCases = medicalCases;
    }

    public Patient(String isim, String soyIsim) {
        super(isim, soyIsim);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", isim='" + isim + '\'' +
                ", soyIsim='" + soyIsim + '\'' +
                ", medicalCases=" + medicalCases +
                ", doctors=" + doctors +
                '}';
    }
}
