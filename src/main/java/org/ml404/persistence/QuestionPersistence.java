package org.ml404.persistence;

import org.ml404.dtos.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPersistence extends JpaRepository<Question, Long> {
}
