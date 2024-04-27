package com.hospitalproject.repository;

import com.hospitalproject.config.HibernateUtils;
import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Title;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MedicalCaseRepository {

    private Session session;
    public void save(MedicalCase medicalCase) {

            try {
                session = HibernateUtils.getSessionFactory().openSession();
                Transaction transaction = session.beginTransaction();
                session.save(medicalCase);
                transaction.commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                HibernateUtils.closeSession(session);
            }
        }

    public MedicalCase getMedicalCaseById(Long id) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            return session.get(MedicalCase.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    public void updateMedicalCase(MedicalCase foundMedicalCase) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(foundMedicalCase);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }
}
