package org.ml404.service.impl;

import org.ml404.dtos.Answer;
import org.ml404.dtos.Consultation;
import org.ml404.dtos.PrescriptionEvaluation;
import org.ml404.dtos.Question;
import org.ml404.persistence.AnswerPersistence;
import org.ml404.persistence.ConsultationPersistence;
import org.ml404.persistence.QuestionPersistence;
import org.ml404.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerPersistence answerPersistence;

    @Autowired
    ConsultationPersistence consultationPersistence;

    @Autowired
    QuestionPersistence questionPersistence;

    @Override
    public Answer createAnswer(Long consultationId, Long questionId, String response) {
        Consultation consultation = consultationPersistence.findById(consultationId).orElseThrow(() -> new IllegalArgumentException("Consultation not found for id: " + consultationId));
        Question question = questionPersistence.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question not found for id: " + questionId));
        Answer answer = new Answer(consultation, question, response);
        return answerPersistence.save(answer);
    }

    @Override
    @Cacheable(value = "answers", key = "#id")
    public List<Answer> getAnswersByConsultationId(Long id) {
        return answerPersistence.findAllById(Collections.singleton(id));
    }

    @Override
    @Cacheable("answers")
    public List<Answer> getAllAnswers() {
        return answerPersistence.findAll();
    }

    @Override
    public void deleteAnswer(Long id) {
        answerPersistence.deleteById(id);
    }

    @Override
    public PrescriptionEvaluation evaluateAnswersForConsultation(Long consultationId) {
        Consultation consultation = consultationPersistence.findById(consultationId).orElseThrow(() -> new IllegalArgumentException("Consultation not found for id: " + consultationId));

        boolean canPrescribe = evaluate(consultation.getAnswers());

        return new PrescriptionEvaluation(canPrescribe);
    }

    private boolean evaluate(List<Answer> answers) {
        // Assumed that the questions are posed so that no answers mean it is suitable, and yes answers mean it's not
        return answers.stream().allMatch(answer -> answer.getResponse().equalsIgnoreCase("no"));
    }
}
