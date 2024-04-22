package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends Person {

    @OneToMany(mappedBy = "patients")
    private List<MedicalCase> medicalCases;

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;




}
