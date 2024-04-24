package com.hospitalproject.controller;

import com.hospitalproject.config.HibernateUtils;
import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.Patient;
import com.hospitalproject.repository.DoctorRepository;
import com.hospitalproject.repository.PatientRepository;
import com.hospitalproject.repository.TitleRepository;
import com.hospitalproject.service.DoctorService;
import com.hospitalproject.service.PatientService;
import com.hospitalproject.service.TitleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class HospitalManagementSystem {

    public static Scanner scan = new Scanner(System.in);

  // public HospitalManagementSystem hospitalManagementSystem = new HospitalManagementSystem();

    public static void start() throws InterruptedException, IOException {


        PatientRepository patientRepository = new PatientRepository();

        TitleRepository titleRepository = new TitleRepository();
        TitleService titleService = new TitleService(titleRepository);

        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorService doctorService = new DoctorService(doctorRepository, titleRepository, titleService);
        PatientService patientService = new PatientService(patientRepository, doctorService);

        HospitalManagementSystem hospitalManagementSystem = new HospitalManagementSystem();

        titleService.saveUnvan();


        int secim = -1;

        do {
            System.out.println("""
                    Lütfen giriş yapmak istediğiniz menü kodunu giriniz..

                    1-HASTANE YÖNETİCİSİ GİRİŞİ
                    2-DOKTOR GİRİŞİ
                    3-HASTA GİRİŞİ
                    4-HASTANE KADROMUZ
                    0-ÇIKIŞ""");

            try {
                secim = scan.nextInt();
            } catch (Exception e) {
                scan.nextLine();
                System.out.println("LUTFEN SIZE SUNULAN SECENEKLERIN DISINDA VERI GIRISI YAPMAYINIZ!");
            }
            switch (secim) {
                case 1:
                    hospitalManagementSystem.hospitalServiceMenu(doctorService,patientService);
                    break;
                case 2:
                    doctorService.doctorEntryMenu();
                    break;
                case 3:
                    patientService.patientEntryMenu(doctorService,patientService);
                    break;
                case 4:
                    contactUs();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("HATALI GIRIS, TEKRAR DENEYINIZ!");
            }
        } while (secim != 0);
    }

    public void hospitalServiceMenu(DoctorService doctorService, PatientService patientService) throws IllegalStateException, IOException, InterruptedException {

        int secim = -1;

        do {
            System.out.println("=========================================");
            System.out.println("""
                    LUTFEN YAPMAK ISTEDIGINIZ ISLEMI SECINIZ:
                    \t=> 1-DOKTOR EKLE
                    \t=> 2-DOKTOR GUNCELLE
                    \t=> 3-TUM DOKTORLARI LISTELE
                    \t=> 4-UNVANA GORE DOKTORLARI LISTELE
                    \t=> 5-DOKTOR SIL
                    \t=> 6-HASTA EKLE
                    \t=> 7-HASTA DUZENLE
                    \t=> 8-HASTALIĞA GÖRE HASTALARI LİSTELE
                    \t=> 9-HASTANIN DURUMUNU BUL
                    \t=> 10-TUM HASTALARI LISTELE
                    \t=> 11-HASTA SIL
                    \t=> 12-HASTANE ISTATISTIKLERINI GOR
                    \t=> 0-ANAMENU""");
            System.out.println("=========================================");
            try {
                secim = scan.nextInt();
                scan.nextLine();//dummy
            } catch (IllegalStateException e) {
                scan.nextLine();
                break;
            } catch (Exception e) {
                scan.nextLine();//dummy
                System.out.println("\"LUTFEN SIZE SUNULAN SECENEKLERIN DISINDA VERI GIRISI YAPMAYINIZ!\"");
                break;
            }
            switch (secim) {
                case 1:
                    doctorService.addDoctor(); //
                    break;
                case 2:
                    doctorService.update();
                    break;
                case 3:
                    doctorService.list();
                    break;
                case 4:
                    doctorService.findDoctorsByTitle();
                    break;
                case 5:
                    doctorService.remove();
                    //
                    break;
                case 6:
                    patientService.add();
                    break;
                case 7:
                    patientService.update();
                    break;
                case 8:
                    patientService.listPatientsByCase();
                    break;
                case 9:
                    System.out.println("HASTANIN DURUMU ACİL Mİ DEĞİL Mİ ÖĞRENMEK İÇİN HASTALIĞINI GİRİNİZ.");
                    String durum = scan.nextLine().trim();
                    System.out.println(patientService.findPatientCase(durum).getEmergency());
                    //acil durumdaki hastaları göstermek için ve acil durumda olmayan hastaları göstermek için
                    //iki ayrı method daha eklenebilir, bu haliyle biraz garip.
                    //Ve burası tamamen başka bir methodun içine alınıp sadeleştirilebilir.
                    break;
                case 10:
                    patientService.list();
                    break;
                case 11:
                    patientService.remove();
                    break;
                case 12:
                    showHospitalStatistics(doctorService, patientService); // Hastane Durumu Metodu
                    break;
                case 0:
                    slowPrint("ANA MENUYE YÖNLENDİRİLİYORSUNUZ...\n", 20);
                    start();
                    break;
                default:
                    System.out.println("HATALI GİRİŞ, TEKRAR DENEYİNİZ...\n");
            }
        } while (secim != 0);
    }

    public void showHospitalStatistics(DoctorService doctorService,PatientService patientService) {
       List<Doctor> doctorList = doctorService.list();
       List<Patient> patientList = patientService.list();

        System.out.println("Hastane İstatistikleri:");
        System.out.println("Toplam Doktor Sayısı: " + doctorList.size());
        System.out.println("Toplam Hasta Sayısı: " + patientList.size());

    }

    public static void contactUs() throws IOException, InterruptedException, IllegalStateException {
        Files.lines(Paths.get("src/main/java/com/hospitalproject/Yonetim.txt")).forEach(System.out::println);
        start();
    }

    public static void exit() {
        try {
          //  slowPrint("\033[32m================== BIZI TERCIH ETTIGINIZ ICIN TESEKKUR EDER SAGLIKLI GUNLER DILERIZ =================\033[0m\n", 20);
            System.out.println();
           // slowPrint("\033[32m======================================= DEV TEAM 02 HASTANESI =======================================\033[0m\n", 20);
        } finally {
            HibernateUtils.shutDown();
            System.out.println("Programdan çıkılıyor...");
            System.exit(0);
        }

    }

    public static void slowPrint(String message, int delay) {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static {
      //  slowPrint("\033[34m============== DEV TEAM 02 HASTANESINE HOSGELDİNİZ ================\033[0m\n", 10);
        // slowPrint("\033[34m================ SAGLIGINIZ BIZIM ICIN ONEMLIDIR ==================\033[0m\n", 10);
    }
}
