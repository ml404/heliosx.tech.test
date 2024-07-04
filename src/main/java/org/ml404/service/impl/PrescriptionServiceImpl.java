package org.ml404.service.impl;

import org.ml404.dtos.Consultation;
import org.ml404.dtos.Doctor;
import org.ml404.dtos.Prescription;
import org.ml404.persistence.ConsultationPersistence;
import org.ml404.persistence.DoctorPersistence;
import org.ml404.persistence.PrescriptionPersistence;
import org.ml404.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    PrescriptionPersistence prescriptionPersistence;

    @Autowired
    ConsultationPersistence consultationPersistence;

    @Autowired
    DoctorPersistence doctorPersistence;

    @Override
    public Prescription createPrescription(Long consultationId, Long doctorId, String status) {
        Consultation consultation = consultationPersistence.findById(consultationId).orElseThrow(() -> new IllegalArgumentException("No consultation found for id: " + consultationId));
        Doctor doctor = doctorPersistence.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("No doctor found for id: " + doctorId));
        Prescription prescription = new Prescription(consultation, doctor, LocalDateTime.now(), Prescription.PrescriptionStatus.PENDING_REVIEW);
        return prescriptionPersistence.save(prescription);
    }

    @Override
    @Cacheable(value = "prescriptions", key = "#id")
    public Prescription getPrescriptionById(Long id) {
        return prescriptionPersistence.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "prescriptions")
    public List<Prescription> getAllPrescriptions() {
        return prescriptionPersistence.findAll();
    }

    @Override
    public void updatePrescriptionStatus(Long id, Prescription.PrescriptionStatus status) {
        Prescription prescription = prescriptionPersistence.findById(id).orElse(null);
        if (prescription != null) {
            prescription.setStatus(status);
            prescriptionPersistence.save(prescription);
        }
    }

    @Override
    public void deletePrescription(Long id) {
        prescriptionPersistence.deleteById(id);
    }
}
