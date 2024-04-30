package com.hospitalproject.repository;

import com.hospitalproject.config.HibernateUtils;

import com.hospitalproject.entity.concretes.Doctor;
import com.hospitalproject.entity.concretes.MedicalCase;
import com.hospitalproject.entity.concretes.Patient;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PatientRepository {

    private Session session;

    public void save(Patient patient) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(patient);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
    }

    public Patient findPatientById(Long id) {

        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Patient patient = session.get(Patient.class, id);
            Hibernate.initialize(patient.getDoctors());
            Hibernate.initialize(patient.getMedicalCases()); //eklendi
            transaction.commit();
            return patient;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }

    public void deletePatient(Patient patientById) {

        try {
            for (Doctor doctor : patientById.getDoctors()) {
                doctor.removePatient(patientById);
            }
            for (MedicalCase medicalCase : patientById.getMedicalCases()) {
                medicalCase.removePatient(patientById);
            }
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(patientById);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

    public List<Patient> findAllPatients() {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            return session.createQuery(
                    "SELECT DISTINCT p " +
                            "FROM Patient p " +
                            "LEFT JOIN FETCH p.medicalCases mc " + // İlişkili tıbbi durumları yükle
                            "LEFT JOIN FETCH mc.doctor d", // İlişkili doktorları yükle
                    Patient.class
            ).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }


    public void updatePatient(Patient patient) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            session.update(patient);

            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }

    }

    public List<Patient> listPatientByCase(String medicalCase) {
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            String hqlQuery = "FROM Patient p WHERE p.medicalCases = medicalCase";
            return session.createQuery(hqlQuery, Patient.class).getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            HibernateUtils.closeSession(session);
        }
        return null;
    }
}
