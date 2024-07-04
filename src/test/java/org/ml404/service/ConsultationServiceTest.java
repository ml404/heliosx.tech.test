package org.ml404.service;

import org.junit.jupiter.api.Test;
import org.ml404.dtos.Consultation;
import org.ml404.dtos.Consultation.ConsultationStatus;
import org.ml404.dtos.Customer;
import org.ml404.persistence.ConsultationPersistence;
import org.ml404.persistence.CustomerPersistence;
import org.ml404.service.impl.ConsultationServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConsultationServiceTest {

    @Mock
    private ConsultationPersistence consultationPersistence;

    @Mock
    private CustomerPersistence customerPersistence;

    @InjectMocks
    private ConsultationServiceImpl consultationService;

    public ConsultationServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartConsultation() {
        // Mock data
        Long customerId = 1L;
        Customer customer = new Customer("John Doe", "john.doe@example.com", "password123");
        customer.setId(customerId);
        Consultation consultation = new Consultation(customer, LocalDateTime.now(), ConsultationStatus.PENDING);

        // Mock repository behavior
        when(customerPersistence.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(consultationPersistence.save(any(Consultation.class))).thenReturn(consultation);

        // Call service method
        Consultation result = consultationService.startConsultation(customerId);

        // Verify
        assertEquals(consultation, result);
        verify(customerPersistence, times(1)).findById(customerId);
        verify(consultationPersistence, times(1)).save(any(Consultation.class));
    }

    @Test
    public void testGetConsultationById() {
        // Mock data
        Long consultationId = 1L;
        Customer customer = new Customer("John Doe", "john.doe@example.com", "password123");
        Consultation expectedConsultation = new Consultation(customer, LocalDateTime.now(), ConsultationStatus.PENDING);

        // Mock repository behavior
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(expectedConsultation));

        // Call service method
        Consultation result = consultationService.getConsultationById(consultationId);

        // Verify
        assertEquals(expectedConsultation, result);
        verify(consultationPersistence, times(1)).findById(consultationId);
    }

    @Test
    public void testGetAllConsultations() {
        // Mock data
        List<Consultation> consultations = List.of(
                new Consultation(new Customer("John Doe", "john.doe@example.com", "password123"), LocalDateTime.now(), ConsultationStatus.PENDING),
                new Consultation(new Customer("Jane Smith", "jane.smith@example.com", "smithpass"), LocalDateTime.now(), ConsultationStatus.COMPLETED)
        );

        // Mock repository behavior
        when(consultationPersistence.findAll()).thenReturn(consultations);

        // Call service method
        List<Consultation> result = consultationService.getAllConsultations();

        // Verify
        assertEquals(consultations.size(), result.size());
        assertEquals(consultations.get(0), result.get(0));
        assertEquals(consultations.get(1), result.get(1));
        verify(consultationPersistence, times(1)).findAll();
    }

    @Test
    public void testUpdateConsultationStatus() {
        // Mock data
        Long consultationId = 1L;
        Consultation consultation = new Consultation(new Customer("John Doe", "john.doe@example.com", "password123"), LocalDateTime.now(), ConsultationStatus.PENDING);
        ConsultationStatus newStatus = ConsultationStatus.COMPLETED;

        // Mock repository behavior
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(consultation));
        when(consultationPersistence.save(any(Consultation.class))).thenReturn(consultation);

        // Call service method
        consultationService.updateConsultationStatus(consultationId, newStatus);

        // Verify
        assertEquals(newStatus, consultation.getStatus());
        verify(consultationPersistence, times(1)).findById(consultationId);
        verify(consultationPersistence, times(1)).save(any(Consultation.class));
    }
}