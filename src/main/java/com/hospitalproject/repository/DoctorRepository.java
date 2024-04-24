package com.hospitalproject.repository;

import com.hospitalproject.config.HibernateUtils;
import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.Title;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorRepository {

    private Session session;

    public void save(Doctor doctor) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(doctor);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    public Doctor findDoctorById(Long id) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            return session.get(Doctor.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    public void deleteDoctor(Doctor doctorById) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.remove(doctorById);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

    public List<Doctor> findAllDoctors() {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            return session.createQuery("FROM Doctor", Doctor.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    public void updateDoctor(Doctor doctor) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            session.update(doctor);

            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

    public List<Doctor> getDoctorListByTitle(Title title){
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            //select * from t_hotel

            String hqlQuery = "FROM Doctor d WHERE d.unvan = :unvan";
            return session.createQuery(hqlQuery, Doctor.class)
                    .setParameter("unvan", title)
                    .getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;

    }
}
