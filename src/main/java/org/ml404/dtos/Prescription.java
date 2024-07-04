package org.ml404.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Schema(description = "Details about the prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "The unique ID of the prescription")
    private Long id;

    @OneToOne
    @JoinColumn(name = "consultation_id")
    @Schema(description = "The unique ID of the consultation associated with the prescription")
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @Schema(description = "The unique ID of the doctor associated with the prescription")
    private Doctor doctor;

    @Column(name = "created_at")
    @Schema(description = "The time the prescription was created")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Schema(description = "The status prescription associated with the prescription")
    private PrescriptionStatus status;

    public enum PrescriptionStatus {
        PENDING_REVIEW,
        APPROVED,
        REJECTED,
        DISPATCHED,
        DELIVERED
    }

    // Constructors, getters, and setters

    public Prescription() {
    }

    public Prescription(Consultation consultation, Doctor doctor, LocalDateTime createdAt, PrescriptionStatus status) {
        this.consultation = consultation;
        this.doctor = doctor;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }
}
