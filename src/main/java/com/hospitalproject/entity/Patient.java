package com.hospitalproject.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "patient")
    private List<Case> cases;

    private int hastaID;

    public Patient(String isim, String soyIsim, int hastaID) {
        super(isim, soyIsim);
        this.hastaID = hastaID;
    }

    public Patient() {
        super();
    }

    public int getHastaID() {
        return hastaID;
    }

    public void setHastaID(int hastaID) {
        this.hastaID = hastaID;
    }

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "Hasta İsmi: " + this.isim + ", Soyisim: " + this.soyIsim + ", Hasta ID: " + this.hastaID;
    }
}
