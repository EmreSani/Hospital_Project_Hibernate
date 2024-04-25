package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient extends Person {


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_doctor",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_medicalcase",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "medicalcase_id")
    )
    private List<MedicalCase> medicalCases;

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
        this.medicalCases = new ArrayList<>(); // medicalCases listesini başlat
        this.doctors = new ArrayList<>(); // doctors listesini başlat
    }

    public Patient() {
        this.medicalCases = new ArrayList<>(); // medicalCases listesini başlat
        this.doctors = new ArrayList<>(); // doctors listesini başlat
    }

    @Override
    public String toString() {
        return "Patient{" +
                "medicalCases=" + medicalCases +
                ", doctors=" + doctors +
                '}';
    }
}
