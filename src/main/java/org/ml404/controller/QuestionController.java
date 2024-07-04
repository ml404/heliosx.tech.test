package org.ml404.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ml404.dtos.Question;
import org.ml404.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Question", description = "Question management APIs")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    @Operation(summary = "Create a new question")
    public ResponseEntity<Question> createQuestion(@RequestParam String text) {
        Question createdQuestion = questionService.createQuestion(text);
        return ResponseEntity.ok(createdQuestion);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a question by ID")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        Question question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping
    @Operation(summary = "Get all questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a question by ID")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
