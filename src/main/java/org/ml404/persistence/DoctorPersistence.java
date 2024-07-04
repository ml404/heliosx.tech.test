package org.ml404.persistence;

import org.ml404.dtos.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorPersistence extends JpaRepository<Doctor, Long> {
}
