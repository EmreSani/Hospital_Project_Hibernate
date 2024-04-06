package com.hospitalproject.service;

import com.hospitalproject.Hospital;
import com.hospitalproject.entity.Case;
import com.hospitalproject.entity.Doctor;
import com.hospitalproject.entity.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
public class HospitalService {
    static Scanner scan = new Scanner(System.in);
    public static Hospital hospital = new Hospital();
    public static HospitalService hospitalService = new HospitalService();
    public static DoctorService doctorService = new DoctorService();
    public static PatientService patientService = new PatientService();

    public void start() throws InterruptedException, IOException {

        Configuration con = new Configuration().
                configure("hibernate.cfg.xml").addAnnotatedClass(Doctor.class).
                addAnnotatedClass(Patient.class).addAnnotatedClass(Case.class);

        SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        tx.commit();

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
                hospitalService.hospitalServiceMenu();
                break;
            case 2:
                doctorService.entryMenu();
                break;
            case 3:
                patientService.entryMenu();
                break;
            case 4:
                contactUs();
                break;
            case 0:
                tx.commit(); //!!! commit önemli,yazılmazsa DB ye kaydedilme garantisi yok..
                session.close();
                sf.close();
                exit();
                break;
            default:
                System.out.println("HATALI GIRIS, TEKRAR DENEYINIZ!");
        }
     } while (secim!=0);

    }


    private void hospitalServiceMenu() throws IllegalStateException, IOException, InterruptedException {
        int secim = -1;
        scan.nextLine();
        do {
            System.out.println("=========================================");
            System.out.println("""
                    LUTFEN YAPMAK ISTEDIGINIZ ISLEMI SECINIZ:
                    \t=> 1-DOKTOR EKLE
                    \t=> 2-DOKTORLARI LISTELE
                    \t=> 3-UNVANDAN DOKTOR BULMA
                    \t=> 4-DOKTOR SIL
                    \t=> 5-HASTA EKLE
                    \t=> 6-HASTA BUL
                    \t=> 7-HASTA LISTELE
                    \t=> 8-HASTA SIL
                    \t=> 0-ANAMENU""");
            System.out.println("=========================================");
            try {
                secim = scan.nextInt();
                scan.nextLine();//dummy
            } catch (IllegalStateException e) {
                break;
            } catch (Exception e) {
                scan.nextLine();//dummy
                System.out.println("\"LUTFEN SIZE SUNULAN SECENEKLERIN DISINDA VERI GIRISI YAPMAYINIZ!\"");
                continue;
            }
            switch (secim) {
                case 1:
                    doctorService.add(); //
                    break;
                case 2:
                    doctorService.list();
                    break;
                case 3:
                    doctorService.findDoctorByTitle();
                    break;
                case 4:
                    doctorService.remove();
                    //
                    break;
                case 5:
                    patientService.add();
                    break;
                case 6:
                    System.out.println("BULMAK İSTEDİĞİNİZ HASTANIN DURUMUNU GİRİNİZ...");
                    String durum = scan.nextLine().trim();
                 //   patientService.listPatientByCase(durum);
                    break;
                case 7:
                    patientService.list();
                    break;
                case 8:
                    patientService.remove();
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

    public void contactUs() throws IOException, InterruptedException, IllegalStateException {
        Files.lines(Paths.get("src/Hospital_Project/Yonetim.txt")).forEach(System.out::println);
        start();
    }

    public static void exit() {
        slowPrint("\033[32m================== BIZI TERCIH ETTIGINIZ ICIN TESEKKUR EDER SAGLIKLI GUNLER DILERIZ =================\033[0m\n", 20);
        System.out.println();
        slowPrint("\033[32m======================================= DEV TEAM 02 HASTANESI =======================================\033[0m\n", 20);
        System.out.println("Programdan çıkılıyor...");
        System.exit(0);
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
     //   slowPrint("\033[34m============== DEV TEAM 02 HASTANESINE HOSGELDİNİZ ================\033[0m\n", 10);
     //   slowPrint("\033[34m================ SAGLIGINIZ BIZIM ICIN ONEMLIDIR ==================\033[0m\n", 10);



        patientService.createList();
        doctorService.createList();
    }

}
