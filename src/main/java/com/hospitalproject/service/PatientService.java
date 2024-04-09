package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static com.hospitalproject.repository.DataBankService.session;
import static com.hospitalproject.repository.DataBankService.tx;
import static com.hospitalproject.service.HospitalService.*;

public class PatientService implements Hospital_Project.Methods {
    private static final LinkedList<Patient> patientList = new LinkedList<>();
    private static final LinkedList<MedicalCase> PATIENT_MEDICAL_CASE_LIST = new LinkedList<>();
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
                    hospitalService.start();
                    break;
                default:
                    System.out.println("HATALI GİRİŞ, TEKRAR DENEYİNİZ...\n");
            }
        } while (secim != 0);
    }

    @Override
    public void add() {
        System.out.println("Eklemek istediginiz hastanin ADINI giriniz");
        String hastaAdi = scan.nextLine();
        System.out.println("Eklemek istediginiz hastanin SOYADINI giriniz");
        String hastaSoyadi = scan.nextLine();
        //      scan.nextLine();
        //   int hastaId = patientList.getLast().getHastaID() + 111; HASTA ID KONUSU?
        String durum;
        String aciliyet;

        do {
            System.out.println("Hastanin Durumu:\n\t=> Allerji\n\t=> Bas agrisi\n\t=> Diabet\n\t=> Soguk alginligi\n\t=> Migren\n\t" +
                    "=> Kalp hastaliklari");
            durum = scan.nextLine().toLowerCase();

        } while (findPatientCase(durum).getActualCase().equalsIgnoreCase("Yanlis Durum"));

        aciliyet = findPatientCase(durum).getEmergency();
        MedicalCase hastaMedicalCase = new MedicalCase(durum, aciliyet);
        Patient patient = new Patient(hastaAdi, hastaSoyadi, hastaMedicalCase); //hasta id konusu?
        Serializable bl = session.save(patient);
        System.out.println(bl + "check this line");
        System.out.println(patient.getId() + patient.getIsim() + patient.getSoyIsim() + " isimli hasta sisteme başarıyla eklenmiştir...");
        list();
    }

    // Hasta Güncelleme Metodu
    public void updatePatient() {
        list();
        System.out.println("Lütfen güncellemek istediğiniz hastanın idsini giriniz");
        Long id = scan.nextLong();
        String hqlQuery = "FROM Patient p WHERE p.id= :id";
        Patient patient = (Patient) session.createQuery(hqlQuery).setParameter("id", id).uniqueResult();
        scan.nextLine();
        System.out.println("İsmi giriniz");
        String isim = scan.nextLine();
        patient.setIsim(isim);
        System.out.println("Soy ismi giriniz");
        String soyIsim = scan.nextLine();
        patient.setSoyIsim(soyIsim);
        System.out.println("Hastalığınızı giriniz");
        String aktuelDurum = scan.nextLine();
        patient.setMedicalCase(findPatientCase(aktuelDurum.toLowerCase()));
        session.update(patient);
        list();
    }

    @Override
    public void remove() {
        list();
        System.out.println("Lutfen silmek istediğiniz hastanın idsini giriniz");
        Long Id = scan.nextLong();

        if (findPatientById(Id) != null) {
            System.out.println(findPatientById(Id) + "isimli hasta sistemden taburcu edildi.");
            session.remove(findPatientById(Id));
            tx.commit();
        } else {
            System.out.println("Bu id'ye sahip hasta sistemde bulunmamaktadır: " + Id);
        }
        list();


        // String hqlQuery = "FROM Patient p WHERE p.Id = Id AND p.isim = name";


    }

    @Override
    public void list() {

        String hqlQuery = "FROM Patient";
        List<Patient> resultList = session.createQuery(hqlQuery, Patient.class).getResultList();

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("----------------------- HASTANEDE BULUNAN HASTALARIMIZ --------------------");
        System.out.printf("%-10s | %-10s | %-15s | %-20s\n", "HASTA ID", "HASTA ISIM", "HASTA SOYISIM", "HASTA DURUM");
        System.out.println("---------------------------------------------------------------------------");
        for (Patient w : resultList) {
            System.out.printf("%-10s | %-10s | %-15s || %-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getMedicalCase());
        }
        System.out.println("---------------------------------------------------------------------------");

    }

    public void listPatientByCase() { //DOĞRU ÇALIŞMIYOR
        System.out.println("Bulmak Istediginiz Hastaların Hastalığını Giriniz:\n\t=> allerji\n\t=> bas agrisi\n\t" +
                "=> diabet\n\t=> soguk alginligi\n\t=> migren\n\t=> kalp hastaliklari");
        String medicalCase = scan.nextLine();

        String hqlQuery = "FROM Patient p WHERE p.medicalCase = medicalCase";
        List<Patient> patientList = session.createQuery(hqlQuery, Patient.class).getResultList(); //NEDEN PATIENT.CLASS ??

        System.out.println("------------------------------------------------------");
        System.out.println("---------- HASTANEDE BULUNAN " + medicalCase.toUpperCase() + " HASTALARIMIZ -----------");
        System.out.printf("%-13s | %-15s | %-15s| %-15s\n", "HASTA ID", "HASTA ISIM ", "HASTA SOYİSİM", "MEDİKAL DURUM");
        System.out.println("------------------------------------------------------");

        for (Patient w : patientList) {
            System.out.printf("%-10s | %-10s | %-15s | %-20s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getMedicalCase());

        }
    }

    public Patient findPatientById(Long id) {

        return session.get(Patient.class, id);
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

}
