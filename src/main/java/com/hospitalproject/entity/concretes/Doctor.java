package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.Entity;

@Entity
public class Doctor extends Person {



  public String unvan;
    public Doctor() {
    }

    public Doctor(String isim, String soyIsim, String unvan) {
        super(isim, soyIsim);
        this.unvan = unvan;
    }

    public Doctor(String unvan) {
        this.unvan = unvan;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;

    }

    @Override
    public String toString() {
        return "Doktor İsmi: " + this.isim + ", Soyisim: " + this.soyIsim + ", Unvan: " + this.unvan;
    }

}
