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

    @ManyToOne
    private Unvan unvan;

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
}
