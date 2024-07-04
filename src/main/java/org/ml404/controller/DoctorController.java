package org.ml404.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ml404.dtos.Doctor;
import org.ml404.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "Doctor", description = "Doctor management APIs")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    @Operation(summary = "Create a new doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody String name, @RequestBody String email) {
        Doctor createdDoctor = doctorService.createDoctor(name, email);
        return ResponseEntity.ok(createdDoctor);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a doctor by ID")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a doctor by ID")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody String name, @RequestBody String email) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, name, email);
        if (updatedDoctor != null) {
            return ResponseEntity.ok(updatedDoctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a doctor by ID")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}