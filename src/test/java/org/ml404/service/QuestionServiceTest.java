package org.ml404.service;

import org.junit.jupiter.api.Test;
import org.ml404.dtos.Question;
import org.ml404.persistence.QuestionPersistence;
import org.ml404.service.impl.QuestionServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionServiceTest {

    @Mock
    private QuestionPersistence questionPersistence;

    @InjectMocks
    private QuestionServiceImpl questionService;

    public QuestionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateQuestion() {
        // Mock data
        String text = "What is your favorite color?";
        Question question = new Question(text);
        Question savedQuestion = new Question(text);

        // Mock repository behavior
        when(questionPersistence.save(any(Question.class))).thenReturn(savedQuestion);

        // Call service method
        Question result = questionService.createQuestion(text);

        // Verify
        assertEquals(savedQuestion, result);
        verify(questionPersistence, times(1)).save(any(Question.class));
    }

    @Test
    public void testGetQuestionById() {
        // Mock data
        Long questionId = 1L;
        String text = "What is your favorite animal?";
        Question expectedQuestion = new Question(text);

        // Mock repository behavior
        when(questionPersistence.findById(questionId)).thenReturn(Optional.of(expectedQuestion));

        // Call service method
        Question result = questionService.getQuestionById(questionId);

        // Verify
        assertEquals(expectedQuestion, result);
        verify(questionPersistence, times(1)).findById(questionId);
    }

    @Test
    public void testGetAllQuestions() {
        // Mock data
        List<Question> questions = List.of(
                new Question("What is your favorite food?"),
                new Question("What is your hobby?")
        );

        // Mock repository behavior
        when(questionPersistence.findAll()).thenReturn(questions);

        // Call service method
        List<Question> result = questionService.getAllQuestions();

        // Verify
        assertEquals(questions.size(), result.size());
        assertEquals(questions.get(0), result.get(0));
        assertEquals(questions.get(1), result.get(1));
        verify(questionPersistence, times(1)).findAll();
    }

    @Test
    public void testDeleteQuestion() {
        // Mock data
        Long questionId = 1L;

        // Call service method
        questionService.deleteQuestion(questionId);

        // Verify
        verify(questionPersistence, times(1)).deleteById(questionId);
    }
}
