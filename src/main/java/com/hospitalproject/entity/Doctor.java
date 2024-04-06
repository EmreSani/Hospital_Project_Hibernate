package com.hospitalproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Doctor extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
