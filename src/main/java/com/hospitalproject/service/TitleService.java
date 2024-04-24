package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Title;
import com.hospitalproject.exceptions.TitleNotFoundException;
import com.hospitalproject.repository.TitleRepository;

import static com.hospitalproject.controller.HospitalManagementSystem.scan;

public class TitleService {

    private final TitleRepository titleRepository;

    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public void saveUnvan() {

        Title title = new Title();
        title.setTitleName("Allergist");
        Title title1 = new Title();
        title1.setTitleName("Norolog");
        Title title2 = new Title();
        title2.setTitleName("Dahiliye");

        titleRepository.save(title);
        titleRepository.save(title1);
        titleRepository.save(title2);

    }

    public Title findUnvanByName() {

        System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
                "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");
        try {
            String unvanName = scan.nextLine();

            Title foundTitle = titleRepository.findTitleByName(unvanName);
            if (foundTitle != null) {
                return foundTitle;
            } else {
                throw new TitleNotFoundException("Unvan is not found by this name : " + unvanName);
            }
        } catch (TitleNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateUnvan(Title title) {
        titleRepository.updateUnvan(title);
    }


}
