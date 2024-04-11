package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
public class Doctor extends Person {

    public String unvan;

    @OneToMany
    private List<Patient> patientList;

    public Doctor() {
    }

    public Doctor(String isim, String soyIsim, String unvan) {
        super(isim, soyIsim);
        this.unvan = unvan.substring(0,1).toUpperCase() + unvan.substring(1).toLowerCase();
    }

    public Doctor(String unvan) {
        this.unvan = unvan;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {

        this.unvan = unvan.substring(0,1).toUpperCase() + unvan.substring(1).toLowerCase();

    }

    @Override
    public String toString() {
        return "Doktor İsmi: " + this.isim + ", Soyisim: " + this.soyIsim + ", Unvan: " + this.unvan;
    }

}
