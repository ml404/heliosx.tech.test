package org.ml404.service;

import org.ml404.dtos.Answer;
import org.ml404.dtos.PrescriptionEvaluation;

import java.util.List;

public interface AnswerService {
    Answer createAnswer(Long consultationId, Long questionId, String response);
    List<Answer> getAnswersByConsultationId(Long id);
    List<Answer> getAllAnswers();
    void deleteAnswer(Long id);
    PrescriptionEvaluation evaluateAnswersForConsultation(Long consultationId);
}