package org.ml404.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Schema(description = "Details about the doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "The unique ID of the doctor")
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(description = "The name of the doctor")
    private String name;

    @Column(name = "email", nullable = false)
    @Schema(description = "The email of the doctor")
    private String email;

    @OneToMany(mappedBy = "doctor")
    @Schema(description = "The prescriptions made by the doctor")
    private List<Prescription> prescriptions;

    // Constructors, getters, and setters

    public Doctor() {
    }

    public Doctor(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
