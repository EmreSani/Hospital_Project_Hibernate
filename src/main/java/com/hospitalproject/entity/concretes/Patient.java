package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;

@Entity
public class Patient extends Person {

    @ManyToOne
    @JoinColumn (name = "doctor_id")
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient(String isim, String soyIsim, Doctor doctor, MedicalCase medicalCase) {
        super(isim, soyIsim);
        this.doctor = doctor;
        this.medicalCase = medicalCase;
    }

    //  @ManyToMany(mappedBy = "patients")
    //  private Set<MedicalCase> medicalCases = new HashSet<>();

    public Patient() {
    }

    // public Set<MedicalCase> getCases() {
    //   return medicalCases;
    //}

    // public void setCases(Set<MedicalCase> medicalCases) {
    // this.medicalCases = medicalCases;
    //}

    //public Patient(String isim, String soyIsim, Set<MedicalCase> medicalCases) {
    //   super(isim, soyIsim);
    //     this.medicalCases = medicalCases;
    //   }

    //  public Patient(Set<MedicalCase> medicalCases) {
    //    this.medicalCases = medicalCases;
    //}

    public Patient(String isim, String soyIsim) {
        super(isim, soyIsim);
    }

    public Patient(String isim, String soyIsim, MedicalCase medicalCase) {
        super(isim, soyIsim);
        this.medicalCase = medicalCase;
    }

    public Patient(MedicalCase medicalCase) {
        this.medicalCase = medicalCase;
    }

    public MedicalCase getMedicalCase() {
        return medicalCase;
    }

    public void setMedicalCase(MedicalCase medicalCase) {
        this.medicalCase = medicalCase;
    }

    @Embedded
    private MedicalCase medicalCase;


    @Override
    public String toString() {
        return "Patient{" +
                "cases=" + medicalCase +
                ", id=" + id +
                ", isim='" + isim + '\'' +
                ", soyIsim='" + soyIsim + '\'' +
                '}';
    }
}
