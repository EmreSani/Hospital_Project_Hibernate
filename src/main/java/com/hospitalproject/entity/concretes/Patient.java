package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends Person {

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;

    @ManyToMany(mappedBy = "patient")
    private List<MedicalCase> medicalCases;



}
