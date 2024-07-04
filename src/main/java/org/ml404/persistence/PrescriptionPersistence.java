package org.ml404.persistence;

import org.ml404.dtos.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionPersistence extends JpaRepository<Prescription, Long> {
}
