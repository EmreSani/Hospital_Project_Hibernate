package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;


@Entity
public class Doctor extends Person {

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Title title;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<MedicalCase> medicalCases;

    @ManyToMany(mappedBy = "doctors", fetch = FetchType.EAGER)
    private List<Patient> patients;


    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
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

    @Override
    public String toString() {
        return "Doctor{" +
                "isim='" + isim + '\'' +
                ", soyIsim='" + soyIsim + '\'' +
                ", title=" + title.getTitleName() +
                '}';
    }
}
