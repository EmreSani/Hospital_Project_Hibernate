package com.hospitalproject.service;

import com.hospitalproject.controller.HospitalManagementSystem;
import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.exceptions.DoctorNotFoundException;
import com.hospitalproject.repository.DoctorRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

import static com.hospitalproject.controller.HospitalManagementSystem.*;

public class DoctorService {
    //private static final LinkedList<Doctor> doctorList = new LinkedList<>();

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void doctorEntryMenu(DoctorService doctorService, PatientService patientService) throws InterruptedException, IOException {

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
            String doktorUnvan = scan.nextLine().trim().toLowerCase();
            if (!doktorUnvan.matches("allergist|norolog|genel cerrah|cocuk doktoru|dahiliye|kardiolog")) {
                throw new IllegalArgumentException("Geçersiz doktor unvanı.");
            } else {
                doctor.setUnvan(doktorUnvan);
            }

            doctorRepository.save(doctor);

            System.out.println(doctor.getIsim() + " " + doctor.getSoyIsim() + " isimli doktor sisteme başarıyla eklenmiştir...");

            list();
        } catch (Exception e) {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
        } finally {
            //  HospitalManagementSystem.start();
        }
    }

    // Doktor Güncelleme Metodu

    public void update() throws IOException, InterruptedException {

        list();
        System.out.println("Lutfen güncellemek istediğiniz doktorun idsini giriniz");
        Long id = scan.nextLong();
        Doctor foundDoctor = findDoctorById(id);

        if (foundDoctor != null) {
            System.out.println("İsmi giriniz");
            scan.nextLine();
            String isim = scan.nextLine();
            foundDoctor.setIsim(isim);
            System.out.println("Soy ismi giriniz");
            String soyIsim = scan.nextLine();
            foundDoctor.setSoyIsim(soyIsim);
            System.out.println("Unvanı giriniz");
            String unvan = scan.nextLine();
            foundDoctor.setUnvan(unvan);

            doctorRepository.updateDoctor(foundDoctor);
            list();
            // tx.commit();
        } else {
            System.out.println("Lutfen gecerli bir id giriniz." + id + "idsine sahip bir doktor sistemimizde bulunmamaktadir.");
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
        }

    }

    public void remove() throws IOException, InterruptedException {

        list();
        System.out.println("Lutfen silmek istediğiniz doktorun idsini giriniz");
        Long doctorId = scan.nextLong();
        if (findDoctorById(doctorId) != null) {
            System.out.println(findDoctorById(doctorId).getId() + "idli" + findDoctorById(doctorId).getIsim() +
                    "isimli doktor başarıyla silinmiştir");
            doctorRepository.deleteDoctor(findDoctorById(doctorId));
            list();
        } else
            System.out.println(doctorId + "Idsine sahip doktor sistemimizde bulunmamaktadır lütfen geçerli bir id giriniz.");

        System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");


    }

    public Doctor findDoctorById(Long Id) throws IOException, InterruptedException {
        list();

        System.out.println("Lutfen işlem yapmak istediğiniz doktorun idsini giriniz");
        Id = scan.nextLong();
        scan.nextLine(); //dummy
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


    public void listDoctorsByMedicalCase(String unvan) throws IOException, InterruptedException { //patientın doktorunu seçmek için ünvana göre doktorları listeliyoruz

        try {
            String hqlQuery = "FROM Doctor d WHERE d.unvan = :unvan";
            List<Doctor> doctorList = session.createQuery(hqlQuery, Doctor.class)
                    .setParameter("unvan", unvan.substring(0, 1).toUpperCase() + unvan.substring(1).toLowerCase().trim()) // Parametreyi sorguya ekliyoruz
                    .getResultList();
            if (doctorList != null && !doctorList.isEmpty()) {
                System.out.println("------------------------------------------------------");
                System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
                System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
                System.out.println("------------------------------------------------------");

                for (Doctor w : doctorList) {
                    if (w.getUnvan().equalsIgnoreCase(unvan)) {
                        System.out.printf("%-13s | %-15s | %-15s |%-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getUnvan());
                    }
                }
            } else {
                System.out.println("BU HASTALIĞA BAKAN DOKTORUMUZ BULUNMAMAKTADIR");
                slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
                hospitalManagementSystem.hospitalServiceMenu();
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalManagementSystem.hospitalServiceMenu();
            e.printStackTrace();
            System.out.println("------------------------------------------------------");
        } finally {
            session.close();
        }
    }

    public void getDoctorListByTitle(String actualCase) throws IOException, InterruptedException {
        switch (actualCase.toLowerCase().trim()) {
            case "allerji":
                String unvan = "Allergist";
                listDoctorsByMedicalCase(unvan);
                break;
            case "bas agrisi":
                String unvan1 = "Norolog";
                listDoctorsByMedicalCase(unvan1);
                break;
            case "diabet":
                String unvan2 = "Genel cerrah";
                listDoctorsByMedicalCase(unvan2);
                break;
            case "soguk alginligi":
                String unvan3 = "Cocuk doktoru";
                listDoctorsByMedicalCase(unvan3);
                break;
            case "migren":
                String unvan4 = "Dahiliye uzmanı";
                listDoctorsByMedicalCase(unvan4);
                break;
            case "kalp hastaliklari":
                String unvan5 = "Kardiolog";
                listDoctorsByMedicalCase(unvan5);
                break;
            default:
                String unvan6 = "";
                listDoctorsByMedicalCase(unvan6);

        }
    }


    public void findDoctorsByTitle() throws IOException, InterruptedException {

        System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
                "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");

        try {
            String unvan = scan.nextLine().toLowerCase().trim();

           List<Doctor> resultList = doctorRepository.getDoctorListByTitle(unvan);

            if (resultList != null) {
                System.out.println("------------------------------------------------------");
                System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
                System.out.printf("%-13s |%-13s | %-15s | %-15s\n","DOKTOR ID", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
                System.out.println("------------------------------------------------------");

                for (Doctor w : resultList) {
                    System.out.printf("%-13s | %-15s | %-15s |%-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getUnvan());
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
                System.out.printf("%-13s | %-15s | %-15s| %-15s \n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getUnvan());
            }
            return doctorList;
        } else {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            System.out.println("------------------------------------------------------");
            return null;
        }

    }
}
