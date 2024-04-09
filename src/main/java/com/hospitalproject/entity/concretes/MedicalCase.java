package com.hospitalproject.entity.concretes;

import javax.persistence.*;

@Embeddable
public class MedicalCase {

  //  private Set<Patient> patients = new HashSet<>();
    private String actualCase;
    private String emergency;

    public MedicalCase() {
    }

    public MedicalCase(String actualCase, String emergency) {
        this.actualCase = actualCase;
        this.emergency = emergency;
    }

    @Override
    public String toString() {
        return "Hastalık Durumunuz:'" + actualCase + '\'' +
                ", aciliyet=" + emergency;
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
}
