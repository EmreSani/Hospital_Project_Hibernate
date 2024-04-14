package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;


@Entity
public class Doctor extends Person {

    public String unvan;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalCase> medicalCases;

    @ManyToMany
    private List<Patient> patients;

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
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

    public Doctor(String isim, String soyIsim, String unvan, List<MedicalCase> medicalCases, List<Patient> patients) {
        super(isim, soyIsim);
        this.unvan = unvan;
        this.medicalCases = medicalCases;
        this.patients = patients;
    }

    public Doctor(String isim, String soyIsim, String unvan) {
        super(isim, soyIsim);
        this.unvan = unvan;
    }

    public Doctor() {
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", isim='" + isim + '\'' +
                ", soyIsim='" + soyIsim + '\'' +
                ", unvan='" + unvan + '\'' +
                ", patients=" + patients +
                ", medicalCases=" + medicalCases +
                '}';
    }
}
