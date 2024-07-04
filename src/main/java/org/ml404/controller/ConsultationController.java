package org.ml404.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ml404.dtos.Consultation;
import org.ml404.dtos.Customer;
import org.ml404.service.ConsultationService;
import org.ml404.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@Tag(name = "Consultation", description = "Consultation management APIs")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;


    @Operation(summary = "Start a new consultation")
    @PostMapping
    @ResponseBody
    public ResponseEntity<Consultation> startConsultation(@Parameter(description = "ID of the customer initiating the consultation") @RequestParam Long customerId) {
        Consultation consultation = consultationService.startConsultation(customerId);
        return ResponseEntity.ok(consultation);
    }

    @Operation(summary = "Get a consultation by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@Parameter(description = "ID of the consultation to be fetched") @PathVariable Long id) {
        Consultation consultation = consultationService.getConsultationById(id);
        return ResponseEntity.ok(consultation);
    }

    @Operation(summary = "Get all consultations")
    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultations() {
        List<Consultation> consultations = consultationService.getAllConsultations();
        return ResponseEntity.ok(consultations);
    }

    @Operation(summary = "Update the status of a consultation")
    @PutMapping("/{id}/status")
    public ResponseEntity<Consultation> updateConsultationStatus(
            @Parameter(description = "ID of the consultation to be updated") @PathVariable Long id,
            @Parameter(description = "New status of the consultation") @RequestParam Consultation.ConsultationStatus status) {
        consultationService.updateConsultationStatus(id, status);
        return ResponseEntity.noContent().build();
    }

}