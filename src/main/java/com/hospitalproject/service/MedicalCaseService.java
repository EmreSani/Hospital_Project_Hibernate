package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.repository.MedicalCaseRepository;

public class MedicalCaseService {

    private final MedicalCaseRepository medicalCaseRepository;

    public MedicalCaseService(MedicalCaseRepository medicalCaseRepository) {
        this.medicalCaseRepository = medicalCaseRepository;
    }

    public MedicalCase createMedicalCaseService(String hastalik, String aciliyet) {

        MedicalCase medicalCase = new MedicalCase(hastalik, aciliyet);
        medicalCaseRepository.save(medicalCase);
        return medicalCase;

    }


    //  public void removeMedicalCase(MedicalCase foundMedicalCase) {
    //}

    //public MedicalCase findMedicalCaseByPatientId() {
    //}
}
