package com.hospitalproject.entity.concretes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MedicalCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "medicalCase_patient",
            joinColumns = @JoinColumn(name = "medicalCase_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patients = new HashSet<>();
    private String actualCase;
    private boolean isEmergency;

    public MedicalCase() {
    }

    public MedicalCase(String actualCase, boolean isEmergency) {
        this.actualCase = actualCase;
        this.isEmergency = isEmergency;
    }

    @Override
    public String toString() {
        return "Hastalık Durumunuz:'" + actualCase + '\'' +
                ", aciliyet=" + isEmergency;
    }

    public String getActualCase() {
        return actualCase;
    }

    public void setActualCase(String actualCase) {
        this.actualCase = actualCase;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        this.isEmergency = emergency;
    }
}
