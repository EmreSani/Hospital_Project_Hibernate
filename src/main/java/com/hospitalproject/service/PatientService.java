package com.hospitalproject.service;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;

import com.hospitalproject.exceptions.PatientNotFoundException;
import com.hospitalproject.repository.PatientRepository;


import java.io.IOException;

import java.util.List;

import static com.hospitalproject.controller.HospitalManagementSystem.*;

public class PatientService {

    private final PatientRepository patientRepository;

    private final DoctorService doctorService;

    private final TitleService titleService;

    private final MedicalCaseService medicalCaseService;

    public PatientService(PatientRepository patientRepository, DoctorService doctorService, TitleService titleService, MedicalCaseService medicalCaseService) {
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.titleService = titleService;
        this.medicalCaseService = medicalCaseService;
    }

    private final AppointmentService appointmentService = new AppointmentService();

    public void patientEntryMenu(DoctorService doctorService, PatientService patientService) throws InterruptedException, IOException {

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
                    //   doctorService.findDoctorsByTitle();
                    doctorService.listDoctorsByMedicalCase();
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
                    break;
                default:
                    System.out.println("HATALI GİRİŞ, TEKRAR DENEYİNİZ...\n");
            }
        } while (secim != 0);
    }

    public void add() {

        try {
            System.out.println("Eklemek istediğiniz hastanın ADINI giriniz");
            String hastaAdi = scan.nextLine();
            System.out.println("Eklemek istediğiniz hastanın SOYADINI giriniz");
            String hastaSoyadi = scan.nextLine();

            System.out.println("Lütfen hastalığınızı giriniz...");
            String hastalik = scan.nextLine();
            String aciliyet = findPatientCase(hastalik).getEmergency();

            List<Doctor> doctorList = doctorService.listDoctorsByMedicalCaseWithParameter(hastalik);
            if (doctorList.isEmpty()) {
                System.out.println("Belirttiğiniz hastalığa uygun doktor bulunamadı.");
                return;
            }

            System.out.println("Uzman doktorlarımız arasından doktor seçiniz (id üzerinden):");
            for (Doctor doctor : doctorList) {
                System.out.println("ID: " + doctor.getId() + " - Dr. " + doctor.getIsim());
            }
            Long doctorId = scan.nextLong();
            Doctor doctor = doctorService.findDoctorByIdWithParameter(doctorId);
            if (doctor == null) {
                System.out.println("Belirtilen ID ile doktor bulunamadı.");
                return;
            }

            Patient patient = new Patient(hastaAdi, hastaSoyadi);
            patient.getDoctors().add(doctor);

            MedicalCase hastaMedicalCase = medicalCaseService.createMedicalCaseService(hastalik, aciliyet);
            hastaMedicalCase.setDoctor(doctor);
            hastaMedicalCase.setTitle(doctor.getTitle());
            patient.getMedicalCases().add(hastaMedicalCase);

            patientRepository.save(patient);

            System.out.println(patient.getIsim() + " " + patient.getSoyIsim() + " isimli hasta başarıyla eklenmiştir.");
            System.out.println("Doktorunuz: Dr. " + doctor.getIsim());
        } catch (Exception e) {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            e.printStackTrace();
        }
    }


//    public void add() {
//
//        try {
//            System.out.println("Eklemek istediginiz hastanin ADINI giriniz");
//            String hastaAdi = scan.nextLine();
//            System.out.println("Eklemek istediginiz hastanin SOYADINI giriniz");
//            String hastaSoyadi = scan.nextLine();
//
//            String aciliyet;
//
//
//            System.out.println("Lutfen hastaliğinizi giriniz...");
//            String hastalik = scan.nextLine();
//            aciliyet = findPatientCase(hastalik).getEmergency();
//            List<Doctor> doctorList= doctorService.listDoctorsByMedicalCaseWithParameter(hastalik);
//
//
//            System.out.println("Son olarak uzman doktorlarımız arasından doktor tercihinizi id üzerinden yapınız");
//            Long id = scan.nextLong();
//
//            Doctor doctor = doctorService.findDoctorByIdWithParameter(id);
//            MedicalCase hastaMedicalCase = medicalCaseService.createMedicalCaseService(hastalik,aciliyet);
//
//            if (doctor != null) {
//                //   List<Doctor> doctors = new ArrayList<>();
//                //   doctors.add(doctor);
//                //   List<MedicalCase> medicalCases = new ArrayList<>();
//                //  medicalCases.add(hastaMedicalCase);
//                Patient patient = new Patient(hastaAdi, hastaSoyadi);
//                patient.getMedicalCases().add(hastaMedicalCase); //bu ve alt satırı kontrol et
//                patient.getDoctors().add(doctor);
//                patientRepository.save(patient);
//
//                System.out.println(patient.toString() + patient.getId() + patient.getIsim() + patient.getSoyIsim() + " isimli hasta sisteme başarıyla eklenmiştir... Doktorunuz : " + patient.getDoctors());
//
//                list();
//            } else {
//                System.out.println("doktor seçerken yanlış id girişi yapılmıştır: " + id);
//            }
//        } catch (Exception e) {
//            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
//            e.printStackTrace();
//        }
//    }

    // Hasta Güncelleme Metodu
    public void update() {

        list();
        System.out.println("Lutfen güncellemek istediğiniz hastanın idsini giriniz");
        Long id = scan.nextLong();
        Patient foundPatient = patientRepository.findPatientById(id);

        if (foundPatient != null) {
            System.out.println("İsmi giriniz");
            scan.nextLine();
            String isim = scan.nextLine();
            foundPatient.setIsim(isim);
            System.out.println("Soy ismi giriniz");
            String soyIsim = scan.nextLine();
            foundPatient.setSoyIsim(soyIsim);
            String hastalik = doctorService.listDoctorsByMedicalCase();
            System.out.println("Son olarak uzman doktorlarımız arasından doktor tercihinizi id üzerinden yapınız");
            Long doctorId = scan.nextLong();
            String aciliyet = findPatientCase(hastalik).getEmergency();

            Doctor doctor = doctorService.findDoctorByIdWithParameter(doctorId);
          //  MedicalCase foundMedicalCase = medicalCaseService.findMedicalCaseByPatientId();
            MedicalCase hastaMedicalCase = new MedicalCase(hastalik, aciliyet);

            if (doctor != null) {

             //   medicalCaseService.removeMedicalCase(foundMedicalCase);
                foundPatient.getDoctors().add(doctor);
                foundPatient.getMedicalCases().add(hastaMedicalCase);
                patientRepository.updatePatient(foundPatient);

                System.out.println(foundPatient.getId() + foundPatient.getIsim() + foundPatient.getSoyIsim() + " isimli hasta sisteme başarıyla güncellenmiştir... Doktorunuz : " + foundPatient.getDoctors());

                list();

            } else {
                System.out.println("Lutfen gecerli bir id giriniz." + id + "idsine sahip bir doktor sistemimizde bulunmamaktadir.");
                System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            }

        }
    }



    public void remove() {
        list();
        try {
            System.out.println("Lutfen silmek istediğiniz hastanın idsini giriniz");
            Long Id = scan.nextLong();
           Patient foundPatient = findPatientById(Id);

            if (foundPatient != null) {
                System.out.println(foundPatient + "isimli hasta sistemden taburcu edildi, geçmiş olsun.");
                patientRepository.deletePatient(foundPatient);
            } else {
                System.out.println("Bu id'ye sahip hasta sistemde bulunmamaktadır: " + Id);
            }
            list();
        } catch (Exception e) {
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            e.printStackTrace();
        }
    }


    public List<Patient> list() {

        List<Patient> patientList = patientRepository.findAllPatients();

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("----------------------- HASTANEDE BULUNAN HASTALARIMIZ --------------------");
        System.out.printf("%-10s | %-10s | %-15s | %-20s\n", "HASTA ID", "HASTA ISIM", "HASTA SOYISIM", "HASTA DURUM");
        System.out.println("---------------------------------------------------------------------------");
        if (patientList != null) {
            for (Patient w : patientList) {
                System.out.printf("%-10s | %-10s | %-15s || %-15s\n", w.getId(), w.getIsim(), w.getSoyIsim(), w.getMedicalCases(), w.getDoctors());
            }
            return patientList;
        } else {
            System.out.println("liste boş");

        }

        System.out.println("---------------------------------------------------------------------------");
        return null;

    }

    public void listPatientsByCase() { //DOĞRU ÇALIŞMIYOR
        try {
            System.out.println("Listelemek Istediginiz Hastaların Hastalığını Giriniz:\n\t=> allerji\n\t=> bas agrisi\n\t" +
                    "=> diabet\n\t=> soguk alginligi\n\t=> migren\n\t=> kalp hastaliklari");
            String medicalCase = scan.nextLine().toLowerCase().trim();

            //    MedicalCase medicalCase1 = new MedicalCase();
            //    medicalCase1.setActualCase(medicalCase);

            List<Patient> patientList = patientRepository.listPatientByCase(medicalCase);

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
            System.out.println("İşlem başarısız oldu. Ana menüye yönlendiriliyorsunuz...");
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------------");
    }


    public Patient findPatientById(Long id) {
        list();
        Patient foundPatient=patientRepository.findPatientById(id);
        try {
            if (foundPatient!=null) {
                System.out.println("-----------------------------------------------");
                System.out.println(foundPatient);
                System.out.println("-----------------------------------------------");
                return foundPatient;
            }else{
                throw new PatientNotFoundException("Patient not found by ID: "+id);
            }

        }catch (PatientNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;



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

}
