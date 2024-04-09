package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Doctor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.hospitalproject.repository.DataBankService.session;
import static com.hospitalproject.service.HospitalService.*;

public class DoctorService implements Hospital_Project.Methods {
    private static final LinkedList<Doctor> doctorList = new LinkedList<>();

    @Override
    public void entryMenu() throws InterruptedException, IOException {

        int secim = -1;
        do {
            System.out.println("=========================================");
            System.out.println("""
                    LUTFEN YAPMAK ISTEDIGINIZ ISLEMI SECINIZ:
                    \t=> 1-DOKTORLARI LISTELE
                    \t=> 2-UNVANDAN DOKTOR BULMA
                    \t=> 3-HASTA BULMA
                    \t=> 4-HASTALARI LISTELE\s
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
                    System.out.println("BULMAK İSTEDİĞİNİZ HASTANIN DURUMUNU GİRİNİZ...");
                    String durum = scan.nextLine().trim();
                    //System.out.println(hastaBul(durum));
                    //   patientService.listPatientByCase(durum);
                    //o durumda bir hasta yoksa hicbir sey dondurmuyor
                    break;
                case 4:
                    patientService.list();
                    break;
                case 0:
                    slowPrint("ANA MENUYE YÖNLENDİRİLİYORSUNUZ...\n", 20);
                    hospitalService.start();
                    break;
                default:
                    System.out.println("HATALI GİRİŞ, TEKRAR DENEYİNİZ...\n");
            }
        } while (secim != 0);

    }

    @Override
    public void add() {
        // Doktor Ekleme Metodu
        System.out.println("Eklemek istediginiz doktor ismini giriniz");
        String doktorAdi = scan.nextLine();
        System.out.println("Eklemek istediginiz doktor soy ismini giriniz");
        String doktorSoyadi = scan.nextLine();
        System.out.println("Eklemek İstediginiz doktor Unvanini Giriniz: \n \t=> Allergist\n\t=> Norolog\n\t=> Genel Cerrah\n\t" +
                "=> Cocuk Doktoru\n\t=> Dahiliye\n\t=> Kardiolog");
        String doktorUnvan = scan.nextLine();
        Doctor doctor = new Doctor(doktorAdi, doktorSoyadi, doktorUnvan);
        session.save(doctor);
        System.out.println(doctor.getIsim() + " " + doctor.getSoyIsim() + " isimli doktor sisteme başarıyla eklenmiştir...");
        list();
        // Doktor objesini istersek bir listeye ekleyebilir veya başka bir şekilde saklayabiliriz

    }


    @Override
    public void remove() {
        list();
        System.out.println("Lutfen silmek istediğiniz doktorun idsini giriniz");
        Long doctorId = scan.nextLong();
        if (findDoctorById(doctorId) != null) {
            System.out.println(findDoctorById(doctorId).getId() + "idli" + findDoctorById(doctorId).getIsim() +
                    "isimli doktor başarıyla silinmiştir");
            session.remove(findDoctorById(doctorId));

        } else System.out.println(doctorId +
                doctorId + "Idsine sahip doktor sistemimizde bulunmamaktadır lütfen geçerli bir id giriniz.");
        list();
    }


    public Doctor findDoctorById(Long Id) {
        list();

        System.out.println("Lutfen işlem yapmak istediğiniz doktorun idsini giriniz");

        return session.get(Doctor.class, Id);

    }


    public void findDoctorsByTitle() {
        System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
                "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");
        //scan.nextLine();
        String unvan = scan.nextLine();


        String hqlQuery = "FROM Doctor d WHERE d.unvan=unvan";
        List<Doctor> resultList = session.createQuery(hqlQuery, Doctor.class).getResultList();

        System.out.println("------------------------------------------------------");
        System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
        System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
        System.out.println("------------------------------------------------------");
        boolean varMi = false;

        for (Doctor w : resultList) {
            if (w.getUnvan().equalsIgnoreCase(unvan)) {
                System.out.printf("%-13s | %-15s | %-15s |%-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getUnvan());
                varMi = true;
            }
        }
        if (!varMi) {
            System.out.println("BU UNVANA AİT DOKTOR BULUNMAMAKTADIR");
            slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
        }
        System.out.println("------------------------------------------------------");

    }

    public void list() {

        String hqlQuery = "FROM Doctor";
       List<Doctor> resultList = session.createQuery(hqlQuery, Doctor.class).getResultList();
        System.out.println("------------------------------------------------------");
        System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
        System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
        System.out.println("------------------------------------------------------");
        for (Doctor w : resultList) {
            System.out.printf("%-13s | %-15s | %-15s| %-15s \n",w.getId(), w.getIsim(), w.getSoyIsim(), w.getUnvan());
        }
    }

}
