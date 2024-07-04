package org.ml404.service.impl;

import org.ml404.dtos.Consultation;
import org.ml404.dtos.Consultation.ConsultationStatus;
import org.ml404.dtos.Customer;
import org.ml404.persistence.ConsultationPersistence;
import org.ml404.persistence.CustomerPersistence;
import org.ml404.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    ConsultationPersistence consultationPersistence;

    @Autowired
    CustomerPersistence customerPersistence;

    @Override
    public Consultation startConsultation(Long customerId) {
        Customer customer = customerPersistence.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found for id: " + customerId));
        Consultation consultation = new Consultation(customer, LocalDateTime.now(), ConsultationStatus.PENDING);
        return consultationPersistence.save(consultation);
    }

    @Override
    @Cacheable(value = "consultations", key = "#id")
    public Consultation getConsultationById(Long id) {
        return consultationPersistence.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "consultations")
    public List<Consultation> getAllConsultations() {
        return consultationPersistence.findAll();
    }

    @Override
    public void updateConsultationStatus(Long id, ConsultationStatus status) {
        Consultation consultation = consultationPersistence.findById(id).orElse(null);
        if (consultation != null) {
            consultation.setStatus(status);
            consultationPersistence.save(consultation);
        }
    }
}
