package org.ml404.service;


import org.ml404.dtos.Consultation;
import org.ml404.dtos.Customer;

import java.util.List;

public interface ConsultationService {
    Consultation startConsultation(Long customerId);
    Consultation getConsultationById(Long id);
    List<Consultation> getAllConsultations();
    void updateConsultationStatus(Long id, Consultation.ConsultationStatus status);
}
