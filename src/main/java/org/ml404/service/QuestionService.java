package org.ml404.service;

import org.ml404.dtos.Question;

import java.util.List;

public interface QuestionService {
    Question createQuestion(String text);
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
    void deleteQuestion(Long id);
}
