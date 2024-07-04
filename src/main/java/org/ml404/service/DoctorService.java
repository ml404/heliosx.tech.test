package org.ml404.service;

import org.ml404.dtos.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor createDoctor(String name, String email);

    Doctor updateDoctor(Long id, String name, String email);
    Doctor getDoctorById(Long id);
    List<Doctor> getAllDoctors();
    void deleteDoctor(Long id);
}
