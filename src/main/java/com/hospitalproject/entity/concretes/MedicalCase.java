package com.hospitalproject.entity.concretes;

import javax.persistence.*;
import java.util.List;


@Entity
public class MedicalCase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actualCase;
    private String emergency;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Doctor doctor;

    @ManyToMany(mappedBy = "medicalCases")
    private List<Patient> patients;


    @ManyToOne
    private Title title;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getActualCase() {
        return actualCase;
    }

    public void setActualCase(String actualCase) {
        this.actualCase = actualCase;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public MedicalCase(String emergency, String actualCase) {
        this.emergency = emergency;
        this.actualCase = actualCase;
    }

    public MedicalCase() {
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    @Override
    public String toString() {
        return "MedicalCase{" +
                "actualCase='" + actualCase + '\'' +
                ", emergency='" + emergency + '\'' +
                ", doctor=" + doctor +
                '}';
    }
}
