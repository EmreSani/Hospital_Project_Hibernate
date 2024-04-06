package com.hospitalproject.repository;

import java.util.ArrayList;
import java.util.List;

public class DataBank {
   public List<String> doctorIsimleri = new ArrayList<>();
   public List<String> doctorSoyIsimleri = new ArrayList<>();
   public List<String> unvanlar = new ArrayList<>();
   public List<String> hastaIsimleri = new ArrayList<>();
   public List<String> hastaSoyIsimleri = new ArrayList<>();
   public List<String> durumlar = new ArrayList<>();
   public List<Integer> hastaIDleri = new ArrayList<>();

    {
        // Verilerin başlatılması
        doctorIsimleri.add("Nilson");
        doctorIsimleri.add("John");
        doctorIsimleri.add("Robert");
        doctorIsimleri.add("Marry");
        doctorIsimleri.add("Alan");
        doctorIsimleri.add("Mahesh");

        doctorSoyIsimleri.add("Avery");
        doctorSoyIsimleri.add("Abel");
        doctorSoyIsimleri.add("Erik");
        doctorSoyIsimleri.add("Jacob");
        doctorSoyIsimleri.add("Pedro");
        doctorSoyIsimleri.add("Tristen");

        unvanlar.add("Allergist");
        unvanlar.add("Norolog");
        unvanlar.add("Genel cerrah");
        unvanlar.add("Cocuk doktoru");
        unvanlar.add("Dahiliye");
        unvanlar.add("Kardiolog");

        hastaIsimleri.add("Warren");
        hastaIsimleri.add("Petanow");
        hastaIsimleri.add("Sophia");
        hastaIsimleri.add("Emma");
        hastaIsimleri.add("Darian");
        hastaIsimleri.add("Peter");

        hastaSoyIsimleri.add("Traven");
        hastaSoyIsimleri.add("William");
        hastaSoyIsimleri.add("George");
        hastaSoyIsimleri.add("Tristan");
        hastaSoyIsimleri.add("Luis");
        hastaSoyIsimleri.add("Cole");

        durumlar.add("Allerji");
        durumlar.add("Bas agrisi");
        durumlar.add("Diabet");
        durumlar.add("Soguk alginligi");
        durumlar.add("Migren");
        durumlar.add("Kalp hastaliklari");

        hastaIDleri.add(111);
        hastaIDleri.add(222);
        hastaIDleri.add(333);
        hastaIDleri.add(444);
        hastaIDleri.add(555);
        hastaIDleri.add(666);
    }
    //interfacede ortak methodları toplamanın avantajları neler? Kod açısından çok farketmedi (azalma yönünden)
    //Interfacedeki metoda exception eklediğimizde childda da eklemelimiyiz yoksa kendiliğinden geliyor mu?
    //static instance block tercihi?
    //yeni oluşturduğumuz listeyi veribankasındaki eski listeye ekleme durumu



    // Doktor Silme Metodu
//  public static void removeDoctor(int index) {
//      if (index >= 0 && index < doctorIsimleri.size()) {
//          doctorIsimleri.remove(index);
//          doctorSoyIsimleri.remove(index);
//          unvanlar.remove(index);
//          // Diğer ilgili verileri de buradan silin...
//          System.out.println("Doktor başarıyla silindi.");
//      } else {
//          System.out.println("Geçersiz indeks.");
//      }
//  }

    // Doktor Güncelleme Metodu
    //  public static void updateDoctor(int index, String isim, String soyIsim, String unvan) {
    //      if (index >= 0 && index < doctorIsimleri.size()) {
    //          doctorIsimleri.set(index, isim);
    //          doctorSoyIsimleri.set(index, soyIsim);
    //          unvanlar.set(index, unvan);
    //          // Diğer ilgili verileri de buradan güncelleyin...
    //          System.out.println("Doktor başarıyla güncellendi.");
    //      } else {
    //          System.out.println("Geçersiz indeks.");
    //      }
    //  }

    // Doktor Arama Metodu
    //   public static void searchDoctorByName(String name) {
    //       System.out.println("Aranan doktorlar:");
    //       for (int i = 0; i < doctorIsimleri.size(); i++) {
    //           if (doctorIsimleri.get(i).equalsIgnoreCase(name)) {
    //               System.out.println("İsim: " + doctorIsimleri.get(i) + ", Soyisim: " + doctorSoyIsimleri.get(i) + ", Unvan: " + unvanlar.get(i));
    //           }
    //       }
    //   }

    // Hastane Durumu Metodu
    //   public static void hospitalStatistics() {
    //       System.out.println("Hastane İstatistikleri:");
    //       System.out.println("Toplam Doktor Sayısı: " + doctorIsimleri.size());
    //       System.out.println("Toplam Hasta Sayısı: " + hastaIsimleri.size());
    //       // İlgili diğer istatistikleri burada hesaplayın...
    //   }

    // Hasta Ekleme Metodu
    //   public static void addPatient(String isim, String soyIsim, String durum, int hastaID) {
    //       hastaIsimleri.add(isim);
    //       hastaSoyIsimleri.add(soyIsim);
    //       durumlar.add(durum);
    //       hastaIDleri.add(hastaID);
    //   }

    // Hasta Silme Metodu
    //   public static void removePatient(int index) {
    //       if (index >= 0 && index < hastaIsimleri.size()) {
    //           hastaIsimleri.remove(index);
    //           hastaSoyIsimleri.remove(index);
    //           durumlar.remove(index);
    //           hastaIDleri.remove(index);
    //           System.out.println("Hasta başarıyla silindi.");
    //       } else {
    //           System.out.println("Geçersiz indeks.");
    //       }
    //   }

    // Hasta Güncelleme Metodu
    //  public static void updatePatient(int index, String isim, String soyIsim, String durum, int hastaID) {
    //      if (index >= 0 && index < hastaIsimleri.size()) {
    //          hastaIsimleri.set(index, isim);
    //          hastaSoyIsimleri.set(index, soyIsim);
    //          durumlar.set(index, durum);
    //          hastaIDleri.set(index, hastaID);
    //          System.out.println("Hasta başarıyla güncellendi.");
    //      } else {
    //          System.out.println("Geçersiz indeks.");
    //      }
    //  }

    // Hasta Arama Metodu
    //  public static void searchPatientByName(String name) {
    //      System.out.println("Aranan hastalar:");
    //      for (int i = 0; i < hastaIsimleri.size(); i++) {
    //          if (hastaIsimleri.get(i).equalsIgnoreCase(name)) {
    //              System.out.println("İsim: " + hastaIsimleri.get(i) + ", Soyisim: " + hastaSoyIsimleri.get(i) + ", Durum: " + durumlar.get(i) + ", Hasta ID: " + hastaIDleri.get(i));
    //          }
    //      }
    //  }
}