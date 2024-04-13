package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
public class Doctor extends Person {

    public String unvan;

    @ManyToMany(mappedBy = "doctors")
    private List<MedicalCase> medicalCases;

    @ManyToMany(mappedBy = "doctors")
    private List<Patient> patients;




}
