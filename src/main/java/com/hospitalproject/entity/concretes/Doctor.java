package com.hospitalproject.entity.concretes;

import com.hospitalproject.entity.abstracts.Person;

import javax.persistence.*;
import java.util.List;


@Entity
public class Doctor extends Person {

    @ManyToOne
    private Unvan unvan;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalCase> medicalCases;

    @ManyToMany
    private List<Patient> patients;




}
