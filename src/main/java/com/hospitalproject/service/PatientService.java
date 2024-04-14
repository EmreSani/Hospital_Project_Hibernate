package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.hospitalproject.repository.DataBankService.*;
import static com.hospitalproject.service.HospitalService.*;

public class PatientService implements Hospital_Project.Methods {
    // private static final LinkedList<Patient> patientList = new LinkedList<>();
    // private static final LinkedList<MedicalCase> PATIENT_MEDICAL_CASE_LIST = new LinkedList<>();
    private final AppointmentService appointmentService = new AppointmentService();

    public void entryMenu() throws InterruptedException, IOException {

        int secim = -1;
        do {
            System.out.println("=========================================");
            System.out.println("""
                    LUTFEN YAPMAK ISTEDIGINIZ ISLEMI SECINIZ:
                    \t=> 1-DOKTORLARI LİSTELE
                    \t=> 2-DOKTOR BUL
                    \t=> 3-DURUMUNU OGREN
                    \t=> 4-RANDEVU AL
                    \t=> 0-ANA MENU""");
            System.out.println("=========================================");
            try {
                secim = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("\"LUTFEN SIZE SUNULAN SECENEKLERIN DISINDA VERI GIRISI YAPMAYINIZ!\"");
                continue;
            }
            switch (secim) {
                case 1:
                    doctorService.list();
                    break;
                case 2:
                    doctorService.findDoctorsByTitle();
                    // Thread.sleep(3000);
                    break;
                case 3:
                    System.out.println("DURUMUNUZU ÖĞRENMEK İÇİN HASTALIĞINIZI GİRİNİZ...");
                    String durum = scan.nextLine().trim();
                    System.out.println(findPatientCase(durum));
                    break;
                case 4:
                    appointmentService.haftalikRandevuTableList(appointmentService.haftalikRandevuTable());
                    break;
                case 0:
                    slowPrint("ANA MENUYE YÖNLENDİRİLİYORSUNUZ...\n", 20);
                    // tx.commit();
                    hospitalService.start();
                    break;
                default:
                    System.out.println("HATALI GİRİŞ, TEKRAR DENEYİNİZ...\n");
            }
        } while (secim != 0);
    }

    @Override
    public void add() throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Eklemek istediginiz hastanin ADINI giriniz");
            String hastaAdi = scan.nextLine();
            System.out.println("Eklemek istediginiz hastanin SOYADINI giriniz");
            String hastaSoyadi = scan.nextLine();

            String durum;
            String aciliyet;

            do {
                System.out.println("Hastanin Durumu:\n\t=> Allerji\n\t=> Bas agrisi\n\t=> Diabet\n\t=> Soguk alginligi\n\t=> Migren\n\t" +
                        "=> Kalp hastaliklari");
                durum = scan.nextLine().toLowerCase().trim();

            } while (findPatientCase(durum).getActualCase().equalsIgnoreCase("Yanlis Durum"));

            doctorService.getDoctorListByTitle(durum);
            System.out.println("Son olarak uzman doktorlarımız arasından doktor tercihinizi id üzerinden yapınız");
            long id = scan.nextLong();
            aciliyet = findPatientCase(durum).getEmergency();

            Doctor doctor = doctorService.findDoctorById(id);
            MedicalCase hastaMedicalCase = new MedicalCase(durum, aciliyet);

            if (doctor != null) {
                List<Doctor> doctors = new ArrayList<>();
                doctors.add(doctor);
                List<MedicalCase> medicalCases = new ArrayList<>();
                medicalCases.add(hastaMedicalCase);
                Patient patient = new Patient(hastaAdi, hastaSoyadi, medicalCases, doctors); //hasta id konusu?
                Serializable bl = session.save(patient);
                System.out.println(bl + "check this line");
                System.out.println(patient.getId() + patient.getIsim() + patient.getSoyIsim() + " isimli hasta sisteme başarıyla eklenmiştir...");
                transaction.commit();
                list();
            } else {
                System.out.println("doktor seçerken yanlış id girişi yapılmıştır: " + id);
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
    }

    // Hasta Güncelleme Metodu
    public void updatePatient() throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();
        list();
        try {
            System.out.println("Lütfen güncellemek istediğiniz hastanın idsini giriniz");
            Long id = scan.nextLong();
            String hqlQuery = "FROM Patient p WHERE p.id= :id";
            Patient patient = (Patient) session.createQuery(hqlQuery).setParameter("id", id).uniqueResult();

            if (patient != null) {
                System.out.println("İsmi giriniz");
                scan.nextLine();
                String isim = scan.nextLine();
                patient.setIsim(isim);
                System.out.println("Soy ismi giriniz");
                String soyIsim = scan.nextLine();
                patient.setSoyIsim(soyIsim);
                System.out.println("Hastalığınızı giriniz");
                String aktuelDurum = scan.nextLine();
                MedicalCase mc = findPatientCase(aktuelDurum.toLowerCase());
                List<MedicalCase> mcList = new ArrayList<>();
                mcList.add(mc);
                patient.setMedicalCases(mcList);
                session.update(patient);
                transaction.commit();
                list();
            } else {
                System.out.println("Lutfen gecerli bir id giriniz." + id + "idsine sahip bir hasta sistemimizde bulunmamaktadir.");
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
    }

    @Override
    public void remove() throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();
        list();
        try {
            System.out.println("Lutfen silmek istediğiniz hastanın idsini giriniz");
            Long Id = scan.nextLong();

            if (findPatientById(Id) != null) {
                System.out.println(findPatientById(Id) + "isimli hasta sistemden taburcu edildi, geçmiş olsun.");
                session.remove(findPatientById(Id));
            } else {
                System.out.println("Bu id'ye sahip hasta sistemde bulunmamaktadır: " + Id);
            }
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
        // session = sf.openSession();
        // tx = session.beginTransaction();
        // String hqlQuery = "FROM Patient p WHERE p.Id = Id AND p.isim = name";
    }

    @Override
    public void list() throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String hqlQuery = "FROM Patient";
            List<Patient> resultList = session.createQuery(hqlQuery, Patient.class).getResultList();

            System.out.println("---------------------------------------------------------------------------");
            System.out.println("----------------------- HASTANEDE BULUNAN HASTALARIMIZ --------------------");
            System.out.printf("%-10s | %-10s | %-15s | %-20s\n", "HASTA ID", "HASTA ISIM", "HASTA SOYISIM", "HASTA DURUM");
            System.out.println("---------------------------------------------------------------------------");
            if (resultList!=null){for (Patient w : resultList) {
                System.out.printf("%-10s | %-10s | %-15s || %-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getMedicalCases());
            }}else {System.out.println("liste boş");}


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

        System.out.println("---------------------------------------------------------------------------");

    }

    public void listPatientByCase() throws IOException, InterruptedException { //DOĞRU ÇALIŞMIYOR
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            System.out.println("Listelemek Istediginiz Hastaların Hastalığını Giriniz:\n\t=> allerji\n\t=> bas agrisi\n\t" +
                    "=> diabet\n\t=> soguk alginligi\n\t=> migren\n\t=> kalp hastaliklari");
            String medicalCase = scan.nextLine().toLowerCase().trim();

            String hqlQuery = "FROM Patient p WHERE p.medicalCase = medicalCase";
            List<Patient> patientList = session.createQuery(hqlQuery, Patient.class).getResultList(); //NEDEN PATIENT.CLASS ??

            if (patientList != null) {
                System.out.println("------------------------------------------------------");
                System.out.println("---------- HASTANEDE BULUNAN " + medicalCase.toUpperCase() + " HASTALARIMIZ -----------");
                System.out.printf("%-13s | %-15s | %-15s| %-15s\n", "HASTA ID", "HASTA ISIM ", "HASTA SOYİSİM", "MEDİKAL DURUM");
                System.out.println("------------------------------------------------------");

                for (Patient patient : patientList) {
                    for (MedicalCase patientCase : patient.getMedicalCases()) {
                        if (patientCase.getActualCase().equalsIgnoreCase(medicalCase)) {
                            System.out.printf("Hasta ID: %d, İsim: %s, Soyisim: %s\n", patient.getId(), patient.getIsim(), patient.getSoyIsim());
                            System.out.println("Hasta Tıbbi Durumları:");
                            for (MedicalCase case1 : patient.getMedicalCases()) {
                                System.out.println("\t" + case1.getActualCase());
                            }
                        }
                    }
                }
            } else {
                System.out.println("BU HASTALIĞA YAKALANMIŞ OLAN HASTAMIZ BULUNMAMAKTADIR: " + medicalCase);
                slowPrint("\033[39mANAMENU'YE YONLENDIRILIYORSUNUZ...\033[0m\n", 20);
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

        System.out.println("------------------------------------------------------");
    }

    public Patient findPatientById(Long id) throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            return session.get(Patient.class, id);
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

    public MedicalCase findPatientCase(String aktuelDurum) {
        MedicalCase medicalCase = new MedicalCase("Yanlis Durum", "Geçersiz Durum");
        switch (aktuelDurum.toLowerCase()) {

            case "allerji":
            case "bas agrisi":
            case "diabet":
            case "soguk alginligi":
                medicalCase.setEmergency(" acil bir durum değil");
                medicalCase.setActualCase(aktuelDurum);
                //   medicalCase.setDoctor(doctorService.findDoctorById(sayi));
                break;
            case "migren":
            case "kalp hastaliklari":
                medicalCase.setEmergency(" acil durum");
                medicalCase.setActualCase(aktuelDurum);
                break;
            default:
                System.out.println("Gecerli bir durum degil");
        }
        return medicalCase;
    }

    public void add(Patient patient) throws IOException, InterruptedException {
        Session session = sf1.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            System.out.println("Patient ekleniyor: " + patient.getIsim() + " " + patient.getSoyIsim());
            session.save(patient);
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

        //DataBankService classında uygulamayı ilk run ettiğimizde çok sayıda hastayı ekleyebilmek için yazıldı
        //bir kere kullandıktan sonra lazım olmuyor, şimdilik
    }
}
