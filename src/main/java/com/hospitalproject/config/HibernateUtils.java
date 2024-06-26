package com.hospitalproject.config;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;
import com.hospitalproject.entity.concretes.Title;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().
                    configure("hibernate.cfg.xml").
                    addAnnotatedClass(Title.class).
                    addAnnotatedClass(Doctor.class).
                    addAnnotatedClass(Patient.class).
                    addAnnotatedClass(MedicalCase.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            System.err.println("Initialization of session factory is FAILED!!!");
            e.printStackTrace(); // Hatanın nedenini görmek için hatayı yazdır
        }
    }

    // get sessionfactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // close session factory
    public static void shutDown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    // close session
    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}

