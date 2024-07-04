package org.ml404.persistence;

import org.ml404.dtos.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerPersistence extends JpaRepository<Answer, Long> {

}
