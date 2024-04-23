package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.Unvan;
import com.hospitalproject.exceptions.DoctorNotFoundException;
import com.hospitalproject.exceptions.UnvanNotFoundException;
import com.hospitalproject.repository.UnvanRepository;

import static com.hospitalproject.controller.HospitalManagementSystem.scan;

public class UnvanService {

    private final UnvanRepository unvanRepository;

    public UnvanService(UnvanRepository unvanRepository) {
        this.unvanRepository = unvanRepository;
    }

    public void saveUnvan(String doktorUnvan, Doctor doctor) {

        Unvan unvan = new Unvan();
        unvan.setUnvan(doktorUnvan);
        doctor.setUnvan(unvan);
        unvanRepository.save(unvan);

    }

    public Unvan findUnvanByName() {

        System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
                "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");
        try {
            String unvanName = scan.nextLine();

            Unvan foundUnvan = unvanRepository.findUnvanByName(unvanName);
            if (foundUnvan != null) {
                return foundUnvan;
            } else {
                throw new UnvanNotFoundException("Unvan is not found by this name : " + unvanName);
            }
        } catch (UnvanNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateUnvan(Unvan unvan) {
        unvanRepository.updateUnvan(unvan);
    }


}
