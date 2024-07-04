package org.ml404.persistence;


import org.ml404.dtos.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationPersistence extends JpaRepository<Consultation, Long> {
}
