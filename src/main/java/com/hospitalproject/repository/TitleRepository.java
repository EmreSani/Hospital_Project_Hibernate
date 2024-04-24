package com.hospitalproject.repository;

import com.hospitalproject.config.HibernateUtils;
import com.hospitalproject.entity.concretes.Title;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TitleRepository {

    private Session session;

    public void save(Title title) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(title);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    public void updateUnvan(Title title) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(title);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }


    public Title findTitleByName(String unvanName) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            String hqlQuery = "FROM Title t WHERE t.titleName = :unvanName";
            return session.createQuery(hqlQuery, Title.class)  //BU SATIR ONEMLI
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
