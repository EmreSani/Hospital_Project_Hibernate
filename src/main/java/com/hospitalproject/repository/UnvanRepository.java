package com.hospitalproject.repository;

import com.hospitalproject.config.HibernateUtils;
import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.Unvan;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UnvanRepository {

    private Session session;

    public void save(Unvan unvan) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(unvan);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    public void updateUnvan(Unvan unvan) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(unvan);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }


    public Unvan findUnvanByName(String unvanName) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            String hqlQuery = "FROM Unvan u WHERE u.unvan = :unvanName";
            return session.createQuery(hqlQuery, Unvan.class)  //BU SATIR ONEMLI
                    .setParameter("unvanName", unvanName)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }
}
