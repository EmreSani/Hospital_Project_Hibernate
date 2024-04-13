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

    @ManyToOne
    private Doctor doctor;

    @ManyToMany
    private List<Patient> patients;

    public MedicalCase(String actualCase, String emergency) {
        this.actualCase = actualCase;
        this.emergency = emergency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MedicalCase(List<Patient> patients, Doctor doctor, String actualCase, String emergency) {
        this.patients = patients;
        this.doctor = doctor;
        this.actualCase = actualCase;
        this.emergency = emergency;
    }


    @Override
    public String toString() {
        return "MedicalCase{" +
                "id=" + id +
                ", actualCase='" + actualCase + '\'' +
                ", emergency='" + emergency + '\'' +
                ", doctor=" + doctor +
                ", patients=" + patients +
                '}';
    }
}
