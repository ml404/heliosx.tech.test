package org.ml404.service.impl;

import org.ml404.dtos.Question;
import org.ml404.persistence.QuestionPersistence;
import org.ml404.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionPersistence questionPersistence;

    @Override
    public Question createQuestion(String text) {
        Question question = new Question(text);
        return questionPersistence.save(question);
    }

    @Override
    @Cacheable(value = "questions", key = "#id")
    public Question getQuestionById(Long id) {
        return questionPersistence.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "questions")
    public List<Question> getAllQuestions() {
        return questionPersistence.findAll();
    }

    @Override
    public void deleteQuestion(Long id) {
        questionPersistence.deleteById(id);
    }
}
