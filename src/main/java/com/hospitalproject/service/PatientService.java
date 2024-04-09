package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;

import java.io.IOException;
import java.util.LinkedList;

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
                    doctorService.findDoctorByTitle();
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
        boolean aciliyet;

        do {
            System.out.println("Hastanin Durumu:\n\t=> Allerji\n\t=> Bas agrisi\n\t=> Diabet\n\t=> Soguk alginligi\n\t=> Migren\n\t" +
                    "=> Kalp hastaliklari");
            durum = scan.nextLine().toLowerCase();


        } while (findPatientCase(durum).getActualCase().equalsIgnoreCase("Yanlis Durum"));
        aciliyet = findPatientCase(durum).isEmergency();
        MedicalCase hastaMedicalCase = new MedicalCase(durum, aciliyet);
        Patient patient = new Patient(hastaAdi, hastaSoyadi); //hasta id konusu?
        patientList.add(patient);
        PATIENT_MEDICAL_CASE_LIST.add(hastaMedicalCase);
        System.out.println(patient.getIsim() + patient.getSoyIsim() + " isimli hasta sisteme başarıyla eklenmiştir...");
        list();
    }

    @Override
    public void remove() {

    }

@Override
public void list() {
    System.out.println("---------------------------------------------------------------------------");
    System.out.println("----------------------- HASTANEDE BULUNAN HASTALARIMIZ --------------------");
    System.out.printf("%-10s | %-10s | %-15s | %-20s\n", "HASTA ID", "HASTA ISIM", "HASTA SOYISIM", "HASTA DURUM");
    System.out.println("---------------------------------------------------------------------------");
    for (Patient w : patientList) {
        System.out.printf("%-10s | %-10s | %-15s |\n", w.getIsim(), w.getSoyIsim(), w.getCases());
    }
    System.out.println("---------------------------------------------------------------------------");

}

//  public void listPatientByCase(String aktuelDurum) {
//    for (Patient w : patientList) {
//      if (w.getHastaDurumu().getActualCase().equalsIgnoreCase(aktuelDurum)) {
//        System.out.printf("%-10s | %-10s | %-15s | %-20s\n", w.getHastaID(), w.getIsim(), w.getSoyIsim(), w.getHastaDurumu(), w.getHastaDurumu().isEmergency());
//  }
//}
//}

public Patient findPatient(String aktuelDurum) {
    Patient patient = new Patient();
    for (int i = 0; i < hospital.hastaIsimleri.size(); i++) {

        if (aktuelDurum.equalsIgnoreCase(hospital.durumlar.get(i))) {

            patient.setIsim(hospital.hastaIsimleri.get(i));
            patient.setSoyIsim(hospital.hastaSoyIsimleri.get(i));
            //    patient.setHastaDurumu(findPatientCase(aktuelDurum));
        }
    }
    return patient;
}

public MedicalCase findPatientCase(String aktuelDurum) {
    MedicalCase hastaDurumu = new MedicalCase("Yanlis Durum", false);
    switch (aktuelDurum.toLowerCase()) {
        case "allerji":
        case "bas agrisi":
        case "diabet":
        case "soguk alginligi":
            hastaDurumu.setEmergency(false);
            hastaDurumu.setActualCase(aktuelDurum);
            break;
        case "migren":
        case "kalp hastaliklari":
            hastaDurumu.setEmergency(true);
            hastaDurumu.setActualCase(aktuelDurum);

            break;
        default:
            System.out.println("Gecerli bir durum degil");

    }

    return hastaDurumu;
}

}
