package org.ml404.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ml404.dtos.Consultation;
import org.ml404.dtos.Doctor;
import org.ml404.dtos.Prescription;
import org.ml404.persistence.ConsultationPersistence;
import org.ml404.persistence.DoctorPersistence;
import org.ml404.persistence.PrescriptionPersistence;
import org.ml404.service.impl.PrescriptionServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionPersistence prescriptionPersistence;

    @Mock
    private ConsultationPersistence consultationPersistence;

    @Mock
    private DoctorPersistence doctorPersistence;

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePrescription() {
        // Mock data
        Long consultationId = 1L;
        Long doctorId = 1L;
        String status = "PENDING_REVIEW";

        Consultation consultation = new Consultation();
        consultation.setId(consultationId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        Prescription createdPrescription = new Prescription(consultation, doctor, LocalDateTime.now(), Prescription.PrescriptionStatus.PENDING_REVIEW);

        // Mock repository behavior
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(consultation));
        when(doctorPersistence.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(prescriptionPersistence.save(any(Prescription.class))).thenReturn(createdPrescription);

        // Call service method
        Prescription result = prescriptionService.createPrescription(consultationId, doctorId, status);

        // Verify
        assertEquals(createdPrescription, result);
        verify(consultationPersistence, times(1)).findById(consultationId);
        verify(doctorPersistence, times(1)).findById(doctorId);
        verify(prescriptionPersistence, times(1)).save(any(Prescription.class));
    }

    @Test
    public void testCreatePrescription_ConsultationNotFound() {
        Long consultationId = 1L;
        Long doctorId = 1L;
        String status = "PENDING_REVIEW";

        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.empty());

        // Call service method and verify exception
        assertThrows(IllegalArgumentException.class, () -> prescriptionService.createPrescription(consultationId, doctorId, status));

        verify(consultationPersistence, times(1)).findById(consultationId);
        verify(doctorPersistence, times(0)).findById(doctorId);
        verify(prescriptionPersistence, times(0)).save(any(Prescription.class));
    }

    @Test
    public void testCreatePrescription_DoctorNotFound() {
        Long consultationId = 1L;
        Long doctorId = 1L;
        String status = "PENDING_REVIEW";

        Consultation consultation = new Consultation();
        consultation.setId(consultationId);

        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(consultation));
        when(doctorPersistence.findById(doctorId)).thenReturn(Optional.empty());

        // Call service method and verify exception
        assertThrows(IllegalArgumentException.class, () -> prescriptionService.createPrescription(consultationId, doctorId, status));

        verify(consultationPersistence, times(1)).findById(consultationId);
        verify(doctorPersistence, times(1)).findById(doctorId);
        verify(prescriptionPersistence, times(0)).save(any(Prescription.class));
    }

    @Test
    public void testGetPrescriptionById() {
        // Mock data
        Long prescriptionId = 1L;
        Prescription expectedPrescription = new Prescription();

        // Mock repository behavior
        when(prescriptionPersistence.findById(prescriptionId)).thenReturn(Optional.of(expectedPrescription));

        // Call service method
        Prescription result = prescriptionService.getPrescriptionById(prescriptionId);

        // Verify
        assertEquals(expectedPrescription, result);
        verify(prescriptionPersistence, times(1)).findById(prescriptionId);
    }

    @Test
    public void testGetAllPrescriptions() {
        // Mock data
        List<Prescription> expectedPrescriptions = List.of(
                new Prescription(new Consultation(), new Doctor(), LocalDateTime.now(), Prescription.PrescriptionStatus.PENDING_REVIEW),
                new Prescription(new Consultation(), new Doctor(), LocalDateTime.now(), Prescription.PrescriptionStatus.APPROVED)
        );

        // Mock repository behavior
        when(prescriptionPersistence.findAll()).thenReturn(expectedPrescriptions);

        // Call service method
        List<Prescription> result = prescriptionService.getAllPrescriptions();

        // Verify
        assertEquals(expectedPrescriptions.size(), result.size());
        assertEquals(expectedPrescriptions, result);
        verify(prescriptionPersistence, times(1)).findAll();
    }

    @Test
    public void testUpdatePrescriptionStatus() {
        // Mock data
        Long prescriptionId = 1L;
        Prescription.PrescriptionStatus newStatus = Prescription.PrescriptionStatus.APPROVED;
        Prescription prescription = new Prescription();
        prescription.setStatus(Prescription.PrescriptionStatus.PENDING_REVIEW);

        // Mock repository behavior
        when(prescriptionPersistence.findById(prescriptionId)).thenReturn(Optional.of(prescription));

        // Call service method
        prescriptionService.updatePrescriptionStatus(prescriptionId, newStatus);

        // Verify
        assertEquals(newStatus, prescription.getStatus());
        verify(prescriptionPersistence, times(1)).findById(prescriptionId);
        verify(prescriptionPersistence, times(1)).save(prescription);
    }

    @Test
    public void testDeletePrescription() {
        // Mock data
        Long prescriptionId = 1L;

        // Call service method
        prescriptionService.deletePrescription(prescriptionId);

        // Verify
        verify(prescriptionPersistence, times(1)).deleteById(prescriptionId);
    }
}