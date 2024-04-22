package com.hospitalproject.entity.concretes;

import javax.persistence.*;
import java.util.List;

@Entity
public class Unvan {

    @Id
   @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String unvan;

   @OneToMany (mappedBy = "unvan")
    private List<Doctor> doctors;

   @OneToMany(mappedBy = "unvanlar")
    private List<MedicalCase> medicalCases;

}
