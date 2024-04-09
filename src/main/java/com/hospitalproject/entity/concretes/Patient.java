package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient extends Person {



    @ManyToMany(mappedBy = "patients")
    private Set<MedicalCase> medicalCases = new HashSet<>();

    public Patient() {

    }

    public Set<MedicalCase> getCases() {
        return medicalCases;
    }

    public void setCases(Set<MedicalCase> medicalCases) {
        this.medicalCases = medicalCases;
    }

    public Patient(String isim, String soyIsim, Set<MedicalCase> medicalCases) {
        super(isim, soyIsim);
        this.medicalCases = medicalCases;
    }

    public Patient(Set<MedicalCase> medicalCases) {
        this.medicalCases = medicalCases;
    }

    public Patient(String isim, String soyIsim) {
        super(isim, soyIsim);
    }



    @Override
    public String toString() {
        return "Patient{" +
                "cases=" + medicalCases +
                ", id=" + id +
                ", isim='" + isim + '\'' +
                ", soyIsim='" + soyIsim + '\'' +
                '}';
    }
}
