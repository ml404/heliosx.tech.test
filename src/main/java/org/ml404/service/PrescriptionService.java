package org.ml404.service;

import org.ml404.dtos.Prescription;
import org.ml404.dtos.Prescription.PrescriptionStatus;

import java.util.List;

public interface PrescriptionService {
    Prescription createPrescription(Long consultationId, Long doctorId, String status);

    Prescription getPrescriptionById(Long id);

    List<Prescription> getAllPrescriptions();

    void updatePrescriptionStatus(Long id, PrescriptionStatus status);

    void deletePrescription(Long id);
}
