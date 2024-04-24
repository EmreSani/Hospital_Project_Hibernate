package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.Title;
import com.hospitalproject.exceptions.UnvanNotFoundException;
import com.hospitalproject.repository.TitleRepository;

import static com.hospitalproject.controller.HospitalManagementSystem.scan;

public class TitleService {

    private final TitleRepository titleRepository;

    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public void saveUnvan(String doktorUnvan, Doctor doctor) {

        Title title = new Title();
        title.setUnvan(doktorUnvan);
        doctor.setUnvan(title);
        titleRepository.save(title);

    }

    public Title findUnvanByName() {

        System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
                "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");
        try {
            String unvanName = scan.nextLine();

            Title foundTitle = titleRepository.findUnvanByName(unvanName);
            if (foundTitle != null) {
                return foundTitle;
            } else {
                throw new UnvanNotFoundException("Unvan is not found by this name : " + unvanName);
            }
        } catch (UnvanNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateUnvan(Title title) {
        titleRepository.updateUnvan(title);
    }


}
