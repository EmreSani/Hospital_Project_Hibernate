package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Doctor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

import static com.hospitalproject.repository.DataBankService.*;
import static com.hospitalproject.service.HospitalService.*;

public class DoctorService implements Hospital_Project.Methods {
    //  private static final LinkedList<Doctor> doctorList = new LinkedList<>();

    @Override
    public void entryMenu() throws InterruptedException, IOException {

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
                    patientService.listPatientByCase();
                    break;
                case 4:
                    patientService.list();
                    break;
                case 0:
                    slowPrint("ANA MENUYE YÖNLENDİRİLİYORSUNUZ...\n", 20);
                    tx1.commit();
                    hospitalService.start();
                    break;
                default:
                    System.out.println("HATALI GİRİŞ, TEKRAR DENEYİNİZ...\n");
            }
        } while (secim != 0);

    }

    @Override
    public void add() throws IOException, InterruptedException {
        // Doktor Ekleme Metodu
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Eklemek istediğiniz doktorun adını giriniz:");
            String doktorAdi = scan.nextLine().trim();

            if (doktorAdi.isEmpty()) {
                throw new IllegalArgumentException("Doktor adı boş olamaz.");
            }

            System.out.println("Eklemek istediğiniz doktorun soyadını giriniz:");
            String doktorSoyadi = scan.nextLine().trim();
            if (doktorSoyadi.isEmpty()) {
                throw new IllegalArgumentException("Doktor soyadı boş olamaz.");
            }

            System.out.println("Eklemek İstediginiz doktor Unvanini Giriniz: \n \t=> Allergist\n\t=> Norolog\n\t=> Genel Cerrah\n\t" +
                    "=> Cocuk Doktoru\n\t=> Dahiliye\n\t=> Kardiolog");
            String doktorUnvan = scan.nextLine().trim().toLowerCase();
            if (!doktorUnvan.matches("allergist|norolog|genel cerrah|cocuk doktoru|dahiliye|kardiolog")) {
                throw new IllegalArgumentException("Geçersiz doktor unvanı.");
            }

            Doctor doctor = new Doctor(doktorAdi, doktorSoyadi, doktorUnvan);
            session.save(doctor);
            System.out.println(doctor.getIsim() + " " + doctor.getSoyIsim() + " isimli doktor sisteme başarıyla eklenmiştir...");

            transaction.commit();

            list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalService.hospitalServiceMenu();
            e.printStackTrace();
        } finally {
            session.close();
        }

        //   tx.commit();
        //  session = sf.openSession();
        //  tx = session.beginTransaction();
        // Doktor objesini istersek bir listeye ekleyebilir veya başka bir şekilde saklayabiliriz
    }

    // Doktor Güncelleme Metodu
    public void updateDoctor() throws IOException, InterruptedException {

        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();

        list();
        try {
            System.out.println("Lutfen güncellemek istediğiniz doktorun idsini giriniz");
            Long id = scan.nextLong();
            String hqlQuery = "FROM Doctor d WHERE d.id = :doctorId"; //BURAYI INCELE :doctorId kısmını "variable kullandık hqlde"
            Doctor doctor = (Doctor) session.createQuery(hqlQuery).setParameter("doctorId", id).uniqueResult();

            if (doctor != null) {
                System.out.println("İsmi giriniz");
                scan.nextLine();
                String isim = scan.nextLine();
                doctor.setIsim(isim);
                System.out.println("Soy ismi giriniz");
                String soyIsim = scan.nextLine();
                doctor.setSoyIsim(soyIsim);
                System.out.println("Unvanı giriniz");
                String unvan = scan.nextLine();
                doctor.setUnvan(unvan);
                session.update(doctor);
                transaction.commit();
                list();
                // tx.commit();
            } else {
                System.out.println("Lutfen gecerli bir id giriniz." + id + "idsine sahip bir doktor sistemimizde bulunmamaktadir.");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalService.hospitalServiceMenu();
            e.printStackTrace();
        } finally {
            session.close();
        }

        // session = sf.openSession();
        //  tx = session.beginTransaction();
    }

    @Override
    public void remove() throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            list();
            System.out.println("Lutfen silmek istediğiniz doktorun idsini giriniz");
            Long doctorId = scan.nextLong();
            if (findDoctorById(doctorId) != null) {
                System.out.println(findDoctorById(doctorId).getId() + "idli" + findDoctorById(doctorId).getIsim() +
                        "isimli doktor başarıyla silinmiştir");
                session.remove(findDoctorById(doctorId));
            } else System.out.println(doctorId +
                    doctorId + "Idsine sahip doktor sistemimizde bulunmamaktadır lütfen geçerli bir id giriniz.");
            transaction.commit();
            // tx.commit();
            list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalService.hospitalServiceMenu();
            e.printStackTrace();
        } finally {
            session.close();
        }
        //  session = sf.openSession();
        // tx = session.beginTransaction();
    }

    public Doctor findDoctorById(Long Id) throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();
        list();
        System.out.println("Lutfen işlem yapmak istediğiniz doktorun idsini giriniz");
        try {
            Doctor doctor = session.get(Doctor.class, Id);
            return doctor;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalService.hospitalServiceMenu();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }


    }


    public void listDoctorsByMedicalCase(String unvan) throws IOException, InterruptedException { //patientın doktorunu seçmek için ünvana göre doktorları listeliyoruz

        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();
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
                hospitalService.hospitalServiceMenu();
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalService.hospitalServiceMenu();
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
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("Bulmak Istediginiz Doktorun Unvanini Giriniz:\n\t=> Allergist\n\t=> Norolog\n\t" +
                "=> Genel Cerrah\n\t=> Cocuk Doktoru\n\t=> Dahiliye Uzmanı\n\t=> Kardiolog");

        try {
            String unvan = scan.nextLine();
            String hqlQuery = "FROM Doctor d WHERE d.unvan=:unvan"; //placeholder kullanımı tavsiye edildi.
            List<Doctor> resultList = session.createQuery(hqlQuery, Doctor.class)
                    .setParameter("unvan", unvan.substring(0, 1).toUpperCase() + unvan.substring(1).toLowerCase().trim()) // Parametreyi sorguya ekliyoruz
                    .getResultList();


            if (resultList != null) {
                System.out.println("------------------------------------------------------");
                System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
                System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
                System.out.println("------------------------------------------------------");

                for (Doctor w : resultList) {
                    if (w.getUnvan().equalsIgnoreCase(unvan)) {
                        System.out.printf("%-13s | %-15s | %-15s |%-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getUnvan());
                    }
                }
            } else {
                System.out.println("BU UNVANA AİT DOKTOR BULUNMAMAKTADIR");
                slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalService.hospitalServiceMenu();
            e.printStackTrace();
            System.out.println("------------------------------------------------------");
        } finally {
            session.close();
        }
        System.out.println("------------------------------------------------------");
    }

    public void list() throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String hqlQuery = "FROM Doctor";
            List<Doctor> resultList = session.createQuery(hqlQuery, Doctor.class).getResultList();
            System.out.println("------------------------------------------------------");
            System.out.println("---------- HASTANEDE BULUNAN DOKTORLARİMİZ -----------");
            System.out.printf("%-13s | %-15s | %-15s\n", "DOKTOR İSİM", "DOKTOR SOYİSİM", "DOKTOR UNVAN");
            System.out.println("------------------------------------------------------");
            for (Doctor w : resultList) {
                System.out.printf("%-13s | %-15s | %-15s| %-15s \n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getUnvan());
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // İşlem başarısız olursa geri al
            }
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            hospitalService.hospitalServiceMenu();
            e.printStackTrace();
            System.out.println("------------------------------------------------------");
        } finally {
            session.close();
        }

    }

    public void add(Doctor doctor1) {

        session.save(doctor1);

        //DataBankService classında uygulamayı ilk run ettiğimizde çok sayıda doktoru ekleyebilmek için yazıldı
        //bir kere kullandıktan sonra lazım olmuyor, şimdilik
    }
}
