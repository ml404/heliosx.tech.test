package org.ml404.service;

import org.junit.jupiter.api.Test;
import org.ml404.dtos.Doctor;
import org.ml404.persistence.DoctorPersistence;
import org.ml404.service.impl.DoctorServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DoctorServiceTest {

    @Mock
    private DoctorPersistence doctorPersistence;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    public DoctorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDoctor() {
        // Mock data
        String name = "Dr. John Doe";
        String email = "john.doe@example.com";
        Doctor savedDoctor = new Doctor(name, email);

        // Mock repository behavior
        when(doctorPersistence.save(any(Doctor.class))).thenReturn(savedDoctor);

        // Call service method
        Doctor result = doctorService.createDoctor(name, email);

        // Verify
        assertEquals(savedDoctor, result);
        verify(doctorPersistence, times(1)).save(any(Doctor.class));
    }

    @Test
    public void testGetDoctorById() {
        // Mock data
        Long doctorId = 1L;
        String name = "Dr. John Doe";
        String email = "john.doe@example.com";
        Doctor expectedDoctor = new Doctor(name, email);

        // Mock repository behavior
        when(doctorPersistence.findById(doctorId)).thenReturn(Optional.of(expectedDoctor));

        // Call service method
        Doctor result = doctorService.getDoctorById(doctorId);

        // Verify
        assertEquals(expectedDoctor, result);
        verify(doctorPersistence, times(1)).findById(doctorId);
    }

    @Test
    public void testGetAllDoctors() {
        // Mock data
        List<Doctor> doctors = List.of(
                new Doctor("Dr. John Doe", "john.doe@example.com"),
                new Doctor("Dr. Jane Smith", "jane.smith@example.com")
        );

        // Mock repository behavior
        when(doctorPersistence.findAll()).thenReturn(doctors);

        // Call service method
        List<Doctor> result = doctorService.getAllDoctors();

        // Verify
        assertEquals(doctors.size(), result.size());
        assertEquals(doctors.get(0), result.get(0));
        assertEquals(doctors.get(1), result.get(1));
        verify(doctorPersistence, times(1)).findAll();
    }

    @Test
    public void testDeleteDoctor() {
        // Mock data
        Long doctorId = 1L;

        // Call service method
        doctorService.deleteDoctor(doctorId);

        // Verify
        verify(doctorPersistence, times(1)).deleteById(doctorId);
    }

    @Test
    public void testUpdateDoctor() {
        // Mock data
        Long id = 1L;
        String newName = "Updated Name";
        String newEmail = "updated.email@example.com";

        Doctor existingDoctor = new Doctor("Original Name", "original.email@example.com");
        existingDoctor.setId(id);

        // Mock behavior of repository
        when(doctorPersistence.findById(id)).thenReturn(Optional.of(existingDoctor));
        when(doctorPersistence.save(any(Doctor.class))).thenReturn(existingDoctor);

        // Call the service method
        Doctor updatedDoctor = doctorService.updateDoctor(id, newName, newEmail);

        // Verify that the doctor was updated correctly
        assertEquals(newName, updatedDoctor.getName());
        assertEquals(newEmail, updatedDoctor.getEmail());

        // Verify that save method was called once
        verify(doctorPersistence, times(1)).save(existingDoctor);
    }

    @Test
    public void testUpdateDoctorNotFound() {
        // Mock data
        Long id = 1L;
        String newName = "Updated Name";
        String newEmail = "updated.email@example.com";

        // Mock behavior of repository (doctor not found)
        when(doctorPersistence.findById(id)).thenReturn(Optional.empty());

        // Call the service method
        Doctor updatedDoctor = doctorService.updateDoctor(id, newName, newEmail);

        // Verify that the doctor is null (not found)
        assertEquals(null, updatedDoctor);

        // Verify that save method was never called
        verify(doctorPersistence, never()).save(any(Doctor.class));
    }
}
