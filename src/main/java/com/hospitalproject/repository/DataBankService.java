package com.hospitalproject.repository;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class DataBankService {

    public static Transaction tx;
    public static SessionFactory sf;
    public static Session session;

    static {
        try {
            Configuration config = new Configuration().
                    configure("hibernate.cfg.xml").addAnnotatedClass(Doctor.class).
                    addAnnotatedClass(Patient.class).addAnnotatedClass(MedicalCase.class);

            sf = config.buildSessionFactory();
            session = sf.openSession();
            tx = session.beginTransaction();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }
}
