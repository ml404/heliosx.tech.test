package org.ml404.service;

import org.junit.jupiter.api.Test;
import org.ml404.dtos.*;
import org.ml404.persistence.AnswerPersistence;
import org.ml404.persistence.ConsultationPersistence;
import org.ml404.persistence.QuestionPersistence;
import org.ml404.service.impl.AnswerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AnswerServiceTest {

    @Mock
    private AnswerPersistence answerPersistence;

    @Mock
    private ConsultationPersistence consultationPersistence;

    @Mock
    private QuestionPersistence questionPersistence;

    @InjectMocks
    private AnswerServiceImpl answerService;

    public AnswerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAnswer() {
        // Mock data
        Long consultationId = 1L;
        Long questionId = 1L;
        String response = "Sample response";
        Consultation consultation = new Consultation();
        consultation.setId(consultationId);
        Question question = new Question("Sample question text");
        question.setId(questionId);
        Answer createdAnswer = new Answer(consultation, question, response);

        // Mock repository behavior
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(consultation));
        when(questionPersistence.findById(questionId)).thenReturn(Optional.of(question));
        when(answerPersistence.save(any(Answer.class))).thenReturn(createdAnswer);

        // Call service method
        Answer result = answerService.createAnswer(consultationId, questionId, response);

        // Verify
        assertEquals(createdAnswer, result);
        verify(consultationPersistence, times(1)).findById(consultationId);
        verify(questionPersistence, times(1)).findById(questionId);
        verify(answerPersistence, times(1)).save(any(Answer.class));
    }

    @Test
    public void testCreateAnswerConsultationNotFound() {
        // Mock data
        Long consultationId = 1L;
        Long questionId = 1L;
        String response = "Sample response";

        // Mock behavior of repository (consultation not found)
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.empty());

        // Call service method and expect IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> answerService.createAnswer(consultationId, questionId, response));

        // Verify
        verify(consultationPersistence, times(1)).findById(consultationId);
        verify(questionPersistence,never()).findById(questionId);
        verify(answerPersistence, never()).save(any(Answer.class));
    }

    @Test
    public void testCreateAnswerQuestionNotFound() {
        // Mock data
        Long consultationId = 1L;
        Long questionId = 1L;
        String response = "Sample response";
        Consultation consultation = new Consultation();
        consultation.setId(consultationId);

        // Mock behavior of repository (question not found)
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(consultation));
        when(questionPersistence.findById(questionId)).thenReturn(Optional.empty());

        // Call service method and expect IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> answerService.createAnswer(consultationId, questionId, response));

        // Verify
        verify(consultationPersistence, times(1)).findById(consultationId);
        verify(questionPersistence, times(1)).findById(questionId);
        verify(answerPersistence, never()).save(any(Answer.class));
    }

    @Test
    public void testGetAnswersByConsultationId() {
        // Mock data
        Long consultationId = 1L;
        Answer answer = new Answer();
        answer.setId(1L);
        answer.setConsultation(new Consultation());
        answer.setQuestion(new Question("Sample question text"));
        answer.setResponse("Sample response");

        List<Answer> expectedAnswers = Collections.singletonList(answer);

        // Mock repository behavior
        when(answerPersistence.findAllById(Collections.singleton(consultationId))).thenReturn(expectedAnswers);

        // Call service method
        List<Answer> result = answerService.getAnswersByConsultationId(consultationId);

        // Verify
        assertEquals(expectedAnswers.size(), result.size());
        assertEquals(expectedAnswers.get(0), result.get(0));
        verify(answerPersistence, times(1)).findAllById(Collections.singleton(consultationId));
    }

    @Test
    public void testGetAllAnswers() {
        // Mock data
        List<Answer> expectedAnswers = List.of(
                new Answer(new Consultation(), new Question("Question 1"), "Response 1"),
                new Answer(new Consultation(), new Question("Question 2"), "Response 2")
        );

        // Mock repository behavior
        when(answerPersistence.findAll()).thenReturn(expectedAnswers);

        // Call service method
        List<Answer> result = answerService.getAllAnswers();

        // Verify
        assertEquals(expectedAnswers.size(), result.size());
        assertEquals(expectedAnswers, result);
        verify(answerPersistence, times(1)).findAll();
    }

    @Test
    public void testDeleteAnswer() {
        // Mock data
        Long answerId = 1L;

        // Call service method
        answerService.deleteAnswer(answerId);

        // Verify
        verify(answerPersistence, times(1)).deleteById(answerId);
    }

    @Test
    public void testEvaluateAnswersForConsultationWhenIsSuitable() {
        // Mock data
        Long consultationId = 1L;
        Consultation consultation = new Consultation(new Customer("name1", "email1@email.com", "password"), LocalDateTime.now(), Consultation.ConsultationStatus.COMPLETED);
        Answer answer1 = new Answer(consultation, new Question("Question 1"), "No");
        Answer answer2 = new Answer(consultation, new Question("Question 2"), "No");
        List<Answer> answers = List.of(answer1, answer2);
        consultation.setAnswers(answers);

        // Mock repository behavior
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(consultation));

        // Call service method
        PrescriptionEvaluation result = answerService.evaluateAnswersForConsultation(consultationId);

        // Verify
        assertEquals(true, result.isCanPrescribe());
        verify(consultationPersistence, times(1)).findById(consultationId);
    }

    @Test
    public void testEvaluateAnswersForConsultationReturningNotSuitable() {
        // Mock data
        Long consultationId = 1L;
        Consultation consultation = new Consultation(new Customer("name1", "email1@email.com", "password"), LocalDateTime.now(), Consultation.ConsultationStatus.COMPLETED);
        Answer answer1 = new Answer(consultation, new Question("Question 1"), "No");
        Answer answer2 = new Answer(consultation, new Question("Question 2"), "Yes");
        List<Answer> answers = List.of(answer1, answer2);
        consultation.setAnswers(answers);

        // Mock repository behavior
        when(consultationPersistence.findById(consultationId)).thenReturn(Optional.of(consultation));

        // Call service method
        PrescriptionEvaluation result = answerService.evaluateAnswersForConsultation(consultationId);

        // Verify
        assertEquals(false, result.isCanPrescribe());
        verify(consultationPersistence, times(1)).findById(consultationId);
    }
}
