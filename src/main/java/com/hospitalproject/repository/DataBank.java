package com.hospitalproject.repository;

import java.util.ArrayList;
import java.util.List;

public class DataBank {
    //interfacede ortak methodları toplamanın avantajları neler? Kod açısından çok farketmedi (azalma yönünden)
    //Interfacedeki metoda exception eklediğimizde childda da eklemelimiyiz yoksa kendiliğinden geliyor mu?
    //static instance block tercihi?
    //yeni oluşturduğumuz listeyi veribankasındaki eski listeye ekleme durumu

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

    // Hastane Durumu Metodu
    //   public static void hospitalStatistics() {
    //       System.out.println("Hastane İstatistikleri:");
    //       System.out.println("Toplam Doktor Sayısı: " + doctorIsimleri.size());
    //       System.out.println("Toplam Hasta Sayısı: " + hastaIsimleri.size());
    //       // İlgili diğer istatistikleri burada hesaplayın...
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

}