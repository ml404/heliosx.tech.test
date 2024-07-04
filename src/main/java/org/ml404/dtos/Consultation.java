package org.ml404.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Schema(description = "Represents a medical consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the consultation", example = "1")
    private Long id;

    @ManyToOne
    @Schema(description = "Customer who requested the consultation")
    private Customer customer;

    @Schema(description = "Date and time when the consultation was created", example = "2023-07-03T10:15:30")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the consultation")
    private ConsultationStatus status;

    @OneToMany(mappedBy = "consultation")
    @Schema(description = "List of answers associated with the consultation")
    private List<Answer> answers;

    @OneToOne(mappedBy = "consultation")
    @Schema(description = "Prescription associated with the consultation")
    private Prescription prescription;

    public enum ConsultationStatus {
        PENDING,
        COMPLETED,
        REJECTED
    }

    // Constructors, getters, and setters

    public Consultation() {
    }

    public Consultation(Customer customer, LocalDateTime createdAt, ConsultationStatus status) {
        this.customer = customer;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters and setters for other fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
}


