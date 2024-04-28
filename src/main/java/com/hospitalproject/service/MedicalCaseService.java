package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.repository.MedicalCaseRepository;

import java.util.List;

public class MedicalCaseService {

    private final MedicalCaseRepository medicalCaseRepository;

    public MedicalCaseService(MedicalCaseRepository medicalCaseRepository) {
        this.medicalCaseRepository = medicalCaseRepository;
    }

    public MedicalCase createMedicalCaseService(String aciliyet, String hastalik) {
        MedicalCase medicalCase = new MedicalCase(hastalik, aciliyet);
        medicalCaseRepository.save(medicalCase);
        return medicalCase;
    }

    public MedicalCase findMedicalCaseByPatientId(Long id) {

       return medicalCaseRepository.getMedicalCaseById(id);

    }


    public void update(MedicalCase foundMedicalCase) {

        medicalCaseRepository.updateMedicalCase(foundMedicalCase);

    }
}
