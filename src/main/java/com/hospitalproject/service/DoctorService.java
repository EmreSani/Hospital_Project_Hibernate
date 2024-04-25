package com.hospitalproject.service;

import com.hospitalproject.controller.HospitalManagementSystem;
import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.Title;
import com.hospitalproject.exceptions.DoctorNotFoundException;
import com.hospitalproject.repository.DoctorRepository;
import com.hospitalproject.repository.TitleRepository;
import com.sun.xml.bind.v2.TODO;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

import static com.hospitalproject.controller.HospitalManagementSystem.*;

public class DoctorService {
    //private static final LinkedList<Doctor> doctorList = new LinkedList<>();

    private final DoctorRepository doctorRepository;

    private final TitleRepository titleRepository;

    private final TitleService titleService;

    public DoctorService(DoctorRepository doctorRepository, TitleRepository titleRepository, TitleService titleService) {
        this.doctorRepository = doctorRepository;
        this.titleRepository = titleRepository;
        this.titleService = titleService;
    }

    public void doctorEntryMenu() throws InterruptedException, IOException {

        int secim = -1;
        do {
            System.out.println("=========================================");
            System.out.println("""
                    LUTFEN YAPMAK ISTEDIGINIZ ISLEMI SECINIZ:
                    \t=> 1-DOKTORLARI LISTELE
                    \t=> 2-UNVANDAN DOKTOR BULMA
                    \t=> 3-HASTALIĞA GÖRE HASTALARI LİSTELE
                    \t=> 4-TUM HASTALARI LISTELE\s
                    \t=> 0-ANAMENU""");
            System.out.println("=========================================");
            try {
                secim = scan.nextInt();
                scan.nextLine();//dummy
            } catch (Exception e) {
                System.out.println("\"LUTFEN SIZE SUNULAN SECENEKLERIN DISINDA VERI GIRISI YAPMAYINIZ!\"");
                continue;
            }
            switch (secim) {
                case 1:
                    list();
                    break;
                case 2:
                    findDoctorsByTitle();
                    break;
                case 3:
                    //   patientService.listPatientByCase();
                    break;
                case 4:
                    //  patientService.list();
                    break;
                case 0:
                    slowPrint("ANA MENUYE YÖNLENDİRİLİYORSUNUZ...\n", 20);
                    HospitalManagementSystem.start();
                    break;
                default:
                    System.out.println("HATALI GİRİŞ, TEKRAR DENEYİNİZ...\n");
            }
        } while (secim != 0);

    }


    public void addDoctor() {
        // Doktor Ekleme Metodu
        Doctor doctor = new Doctor();
        try {
            System.out.println("Eklemek istediğiniz doktorun adını giriniz:");
            String doktorAdi = scan.nextLine().trim();


            if (doktorAdi.isEmpty()) {
                throw new IllegalArgumentException("Doktor adı boş olamaz.");
            } else {
                doctor.setIsim(doktorAdi);
            }


            System.out.println("Eklemek istediğiniz doktorun soyadını giriniz:");
            String doktorSoyadi = scan.nextLine().trim();
            if (doktorSoyadi.isEmpty()) {
                throw new IllegalArgumentException("Doktor soyadı boş olamaz.");
            } else {
                doctor.setSoyIsim(doktorSoyadi);
            }

            System.out.println("Eklemek İstediginiz doktor Unvanini Giriniz: \n \t=> Allergist\n\t=> Norolog\n\t=> Genel Cerrah\n\t" +
                    "=> Cocuk Doktoru\n\t=> Dahiliye\n\t=> Kardiolog");
            String doktorUnvan = scan.nextLine().trim();
            String editedDoctorTitle = doktorUnvan.substring(0, 1).toUpperCase().trim() + doktorUnvan.substring(1).toLowerCase().trim();
            if (!editedDoctorTitle.matches("Allergist|Norolog|Genel cerrah|Cocuk doktoru|Dahiliye|Kardiolog")) {
                throw new IllegalArgumentException("Geçersiz doktor unvanı.");
            } else {

                doctor.setTitle(titleRepository.findTitleByName(editedDoctorTitle));
                // titleService.saveUnvan(doktorUnvan, doctor);
            }

            doctorRepository.save(doctor);

            System.out.println(doctor.getIsim() + " " + doctor.getSoyIsim() + " isimli doktor sisteme başarıyla eklenmiştir...");

            list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
        }
    }

    // Doktor Güncelleme Metodu

    public void update() {

        list();
        System.out.println("Lutfen güncellemek istediğiniz doktorun idsini giriniz");

        Long id = null;
        try {
            id = scan.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Lütfen id girişi yapınız.");
            update();
        }
        Doctor foundDoctor = findDoctorByIdWithParameter(id);

        if (foundDoctor != null) {
            System.out.println("İsmi giriniz");
            scan.nextLine();
            String isim = scan.nextLine();
            foundDoctor.setIsim(isim);
            System.out.println("Soy ismi giriniz");
            String soyIsim = scan.nextLine();
            foundDoctor.setSoyIsim(soyIsim);

            System.out.println("Yeni ünvanı giriniz");
            String doctorTitle = scan.nextLine();
            String editedTitleName = doctorTitle.substring(0, 1).toUpperCase().trim() + doctorTitle.substring(1).toLowerCase().trim();
            foundDoctor.setTitle(titleRepository.findTitleByName(editedTitleName));

            doctorRepository.updateDoctor(foundDoctor);
            list();
        } else {
            System.out.println("Lutfen gecerli bir id giriniz." + id + "idsine sahip bir doktor sistemimizde bulunmamaktadir.");
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
        }

    }

    public void remove() {

        list();
        System.out.println("Lutfen silmek istediğiniz doktorun idsini giriniz");
        Long doctorId = scan.nextLong();
        if (findDoctorByIdWithParameter(doctorId) != null) {
            System.out.println(findDoctorByIdWithParameter(doctorId).getId() + " idli " + findDoctorByIdWithParameter(doctorId).getIsim() +
                    "isimli doktor başarıyla silinmiştir");
            // Doctor doctorToRemove = findDoctorByIdWithParameter(doctorId);
            // doctorToRemove.getUnvan().
            // doctorRepository.deleteDoctor(doctorToRemove);
            doctorRepository.deleteDoctor(findDoctorByIdWithParameter(doctorId));
            list();
        } else {
            System.out.println(doctorId + "Idsine sahip doktor sistemimizde bulunmamaktadır lütfen geçerli bir id giriniz.");
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
        }
    }

    public Doctor findDoctorByIdWithoutParameter() {
        list();

        System.out.println("Lutfen işlem yapmak istediğiniz doktorun idsini giriniz");
        Long id = scan.nextLong();
        scan.nextLine(); //dummy
        Doctor foundDoctor = doctorRepository.findDoctorById(id);

        try {
            if (foundDoctor != null) {
                return foundDoctor;
            } else {
                throw new DoctorNotFoundException("Doctor is not found by this id : " + id);
            }
        } catch (DoctorNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Doctor findDoctorByIdWithParameter(Long Id) {
        list();
        Doctor foundDoctor = doctorRepository.findDoctorById(Id);
        try {
            if (foundDoctor != null) {
                return foundDoctor;
            } else {
                throw new DoctorNotFoundException("Doctor is not found by this id : " + Id);
            }
        } catch (DoctorNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Doctor> listDoctorsByMedicalCaseWithParameter(String name) { //patientın doktorunu seçmek için ünvana göre doktorları listeliyoruz

        try {
            Title foundTitle = caseToTitle(name);
            //  Title donenTitle = titleService.findUnvanByNameWithParameter(name);
            //  Title donenTitle = caseToTitle(hastalik);
            List<Doctor> doctorList = doctorRepository.getDoctorListByTitle(foundTitle);
            if (doctorList != null && !doctorList.isEmpty()) {
                System.out.println("------------------------------------------------------");
                System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
                System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
                System.out.println("------------------------------------------------------");

                for (Doctor w : doctorList) {
                    System.out.printf("%-13s | %-15s | %-15s |%-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getTitle());
                }
                return doctorList;
            } else {
                System.out.println("BU HASTALIĞA BAKAN DOKTORUMUZ BULUNMAMAKTADIR");
                slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
            }

        } catch (Exception e) {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            System.out.println("------------------------------------------------------");
        }
        return null;
    }


    public String listDoctorsByMedicalCase() { //patientın doktorunu seçmek için ünvana göre doktorları listeliyoruz

        try {
            System.out.println("Lutfen hastalığınızı giriniz.");
            String hastalik = scan.nextLine().trim();
            Title donenTitle = caseToTitle(hastalik);
            List<Doctor> doctorList = doctorRepository.getDoctorListByTitle(donenTitle);
            if (doctorList != null && !doctorList.isEmpty()) {
                System.out.println("------------------------------------------------------");
                System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
                System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
                System.out.println("------------------------------------------------------");

                for (Doctor w : doctorList) {
                    System.out.printf("%-13s | %-15s | %-15s |%-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getTitle());
                }
                return hastalik;
            } else {
                System.out.println("BU HASTALIĞA BAKAN DOKTORUMUZ BULUNMAMAKTADIR");
                slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
            }

        } catch (Exception e) {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            System.out.println("------------------------------------------------------");
        }
        return null;
    }

    public Title caseToTitle(String actualCase) {

        Title foundTitle;
        //ToDo: küçük büyük harf kontrolü lazım
        switch (actualCase.toLowerCase().trim()) {
            case "allerji":
                foundTitle =   titleService.findUnvanByNameWithParameter("Allergist");
                break;
            case "bas agrisi":
                foundTitle =   titleService.findUnvanByNameWithParameter("Norolog");
                break;
            case "diabet":
                foundTitle =   titleService.findUnvanByNameWithParameter("Genel cerrah");
                break;
            case "soguk alginligi":
                foundTitle =   titleService.findUnvanByNameWithParameter("Cocuk doktoru");
                break;
            case "migren":
                foundTitle =   titleService.findUnvanByNameWithParameter("Dahiliye uzmanı");
                break;
            case "kalp hastaliklari":
              foundTitle = titleService.findUnvanByNameWithParameter("Kardiolog");
                break;
            default:
                System.out.println("geçersiz ünvan");
                return null;
        }

        return foundTitle; // Güncellenmiş Unvan nesnesini döndür
    }


    public void findDoctorsByTitle() {

        Title foundTitle = titleService.findUnvanByName();

        //  System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
        //        "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");

        try {
            //     String unvan = scan.nextLine().toLowerCase().trim();
            //   String editedUnvan = unvan.substring(0, 1).toUpperCase() + unvan.substring(1).toLowerCase().trim();
            // Unvan unvan2 = new Unvan();
            //unvan2.setUnvan(editedUnvan);

            List<Doctor> resultList = doctorRepository.getDoctorListByTitle(foundTitle);

            if (resultList != null && !resultList.isEmpty()) {
                System.out.println("------------------------------------------------------");
                System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
                System.out.printf("%-13s |%-13s | %-15s | %-15s\n", "DOKTOR ID", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
                System.out.println("------------------------------------------------------");

                for (Doctor w : resultList) {
                    System.out.printf("%-13s | %-15s | %-15s |%-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getTitle().getTitleName());
                }
            } else {
                System.out.println("BU UNVANA AİT DOKTOR BULUNMAMAKTADIR");
                slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
            }
        } catch (Exception e) {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            e.printStackTrace();
            System.out.println("------------------------------------------------------");
        }
        System.out.println("------------------------------------------------------");
    }

    public List<Doctor> list() {

        List<Doctor> doctorList = doctorRepository.findAllDoctors();
        if (doctorList != null) {
            System.out.println("------------------------------------------------------");
            System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
            System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
            System.out.println("------------------------------------------------------");
            for (Doctor w : doctorList) {
                System.out.printf("%-13s | %-15s | %-15s| %-15s \n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getTitle().getTitleName());
            }
            return doctorList;
        } else {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            System.out.println("------------------------------------------------------");
            return null;
        }

    }
}
