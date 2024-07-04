package org.ml404.service.impl;

import org.ml404.dtos.Doctor;
import org.ml404.persistence.DoctorPersistence;
import org.ml404.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {


    @Autowired
    DoctorPersistence doctorPersistence;

    @Override
    public Doctor createDoctor(String name, String email) {
        Doctor doctor = new Doctor(name, email);
        return doctorPersistence.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, String name, String email) {
        Doctor doctor = doctorPersistence.findById(id).orElse(null);
        if (doctor != null) {
            doctor.setName(name);
            doctor.setEmail(email);

            doctorPersistence.save(doctor);
        }
        return doctor;
    }

    @Override
    @Cacheable(value = "doctors", key = "#id")
    public Doctor getDoctorById(Long id) {
        return doctorPersistence.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "doctors")
    public List<Doctor> getAllDoctors() {
        return doctorPersistence.findAll();
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorPersistence.deleteById(id);
    }
}
