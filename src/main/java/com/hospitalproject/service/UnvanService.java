package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.Unvan;
import com.hospitalproject.repository.UnvanRepository;

import static com.hospitalproject.controller.HospitalManagementSystem.scan;

public class UnvanService {

    private final UnvanRepository unvanRepository;

    public UnvanService(UnvanRepository unvanRepository) {
        this.unvanRepository = unvanRepository;
    }

    public void saveUnvan (String doktorUnvan, Doctor doctor){

        Unvan unvan = new Unvan();
        unvan.setUnvan(doktorUnvan);
        doctor.setUnvan(unvan);
        unvanRepository.save(unvan);

    }

    public void updateUnvan(Unvan unvan){
        unvanRepository.updateUnvan(unvan);
    }


}
