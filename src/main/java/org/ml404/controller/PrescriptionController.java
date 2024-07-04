package org.ml404.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ml404.dtos.Prescription;
import org.ml404.dtos.Prescription.PrescriptionStatus;
import org.ml404.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@Tag(name = "Prescription", description = "Prescription management APIs")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    @Operation(summary = "Create a new prescription")
    public ResponseEntity<Prescription> createPrescription(@RequestParam Long consultationId, @RequestParam Long doctorId, @RequestParam String status) {
        Prescription createdPrescription = prescriptionService.createPrescription(consultationId, doctorId, status);
        return ResponseEntity.ok(createdPrescription);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a prescription by ID")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping
    @Operation(summary = "Get all prescriptions")
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptions);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update a prescription's status by ID")
    public ResponseEntity<Prescription> updatePrescriptionStatus(@PathVariable Long id, @RequestParam PrescriptionStatus status) {
        prescriptionService.updatePrescriptionStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a prescription by ID")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}
