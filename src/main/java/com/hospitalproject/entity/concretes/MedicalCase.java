package com.hospitalproject.entity.concretes;

import javax.persistence.*;
import java.util.List;

@Entity
public class MedicalCase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actualCase;
    private String emergency;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private List<Patient> patients;


}
