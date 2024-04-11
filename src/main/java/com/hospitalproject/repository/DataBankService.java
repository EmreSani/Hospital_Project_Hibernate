package com.hospitalproject.repository;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;
import com.hospitalproject.service.DoctorService;
import com.hospitalproject.service.PatientService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;


public class DataBankService {

    public static Transaction tx1;
    public static SessionFactory sf1;
    public static Session session;

    public static DoctorService ds = new DoctorService();
    public static PatientService ps = new PatientService();

    static {
        try {
            Configuration config = new Configuration().
                    configure("hibernate.cfg.xml").addAnnotatedClass(Doctor.class).
                    addAnnotatedClass(Patient.class).addAnnotatedClass(MedicalCase.class);

            sf1 = config.buildSessionFactory();
            session = sf1.openSession();
            tx1 = session.beginTransaction();



            Doctor doctor = new Doctor("Emre", "SANI", "Norolog");
            Doctor doctor1 = new Doctor("Sukru", "HARMANCI", "Allergist");
            Doctor doctor2 = new Doctor("Ahmet", "DIKBAYIR", "Cocuk Doktoru");
            Doctor doctor3 = new Doctor("Ayse", "BATTAL", "Norolog");
            Doctor doctor4 = new Doctor("Dogu", "BEY", "Allergist");
            Doctor doctor5 = new Doctor("Busra", "OZER", "Kardiolog");
            Doctor doctor6 = new Doctor("Osman", "SEYBAN", "Genel Cerrah");
            Doctor doctor7 = new Doctor("Aybars", "KUCUKAYDIN", "Dahiliye");
            Doctor doctor8 = new Doctor("Hakan", "KARATAY", "Norolog");
            Doctor doctor9 = new Doctor("Sefa", "EYER", "kardiolog");
            Doctor doctor10 = new Doctor("Omer Faruk", "FILIZ", "genel Cerrah");
            ds.add(doctor);
            ds.add(doctor1);
            ds.add(doctor2);
            ds.add(doctor3);
            ds.add(doctor4);
            ds.add(doctor5);
            ds.add(doctor6);
            ds.add(doctor7);
            ds.add(doctor8);
            ds.add(doctor9);
            ds.add(doctor10);

            tx1.commit();

                  Patient patient = new Patient("Emre", "SANI");
                  patient.setMedicalCase(ps.findPatientCase("kalp hastaliklari"));
//
                  Patient patient2 = new Patient("Said", "PATLAR");
                  patient2.setMedicalCase(ps.findPatientCase("allerji"));
//
                  Patient patient3 = new Patient("Burak", "GUR");
                  patient3.setMedicalCase(ps.findPatientCase("soguk alginligi"));
//
                  Patient patient4 = new Patient("Harun", "YILDIRIM");
                  patient4.setMedicalCase(ps.findPatientCase("soguk alginligi"));
//
                  Patient patient5 = new Patient("Cafer", "KOC");
                  patient5.setMedicalCase(ps.findPatientCase("bas agrisi"));
//
                  Patient patient6 = new Patient("Sukru", "OZKACAR");
                  patient6.setMedicalCase(ps.findPatientCase("diabet"));
//
                  Patient patient7 = new Patient("Halil", "GUZEL");
                  patient7.setMedicalCase(ps.findPatientCase("migren"));
//
                  Patient patient8 = new Patient("Gunduz", "ATALAY");
                  patient8.setMedicalCase(ps.findPatientCase("bas agrisi"));
//
                  Patient patient9 = new Patient("Mali", "SOYLU");
                  patient9.setMedicalCase(ps.findPatientCase("bas agrisi"));
//
                 Patient patient10 = new Patient("Batuhan", "KOSE");
                 patient10.setMedicalCase(ps.findPatientCase("diabet"));
                 patient10.setDoctor(doctor6);


                  ps.add(patient);
                  ps.add(patient2);
                  ps.add(patient3);
                  ps.add(patient4);
                  ps.add(patient5);
                  ps.add(patient6);
                  ps.add(patient7);
                  ps.add(patient8);
                  ps.add(patient9);
                 ps.add(patient10);


        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
