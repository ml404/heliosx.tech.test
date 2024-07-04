package org.ml404.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ml404.dtos.Answer;
import org.ml404.dtos.PrescriptionEvaluation;
import org.ml404.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@Tag(name = "Answer", description = "Answer management APIs")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    @Operation(summary = "Create a new answer")
    public ResponseEntity<Answer> createAnswer(
            @RequestParam Long consultationId, @RequestParam Long questionId, @RequestParam String response
    ) {
        Answer createdAnswer = answerService.createAnswer(consultationId, questionId, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get answer by Consultation ID")
    public ResponseEntity<List<Answer>> getAnswersByConsultationId(
            @Parameter(description = "Answers to be obtained associated with the specified consultation id", required = true, example = "1", in = ParameterIn.PATH)
            @PathVariable Long id
    ) {
        List<Answer> answers = answerService.getAnswersByConsultationId(id);
        if (!answers.isEmpty()) {
            return ResponseEntity.ok(answers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all answers")
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete answer by ID")
    public ResponseEntity<Void> deleteAnswer(
            @Parameter(description = "ID of the answer to be deleted", required = true, example = "1", in = ParameterIn.PATH)
            @PathVariable Long id
    ) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/evaluate/{consultationId}")
    @Operation(summary = "Evaluate answers for a consultation to determine if a prescription can be made")
    public ResponseEntity<PrescriptionEvaluation> evaluateAnswers(@PathVariable Long consultationId) {
        PrescriptionEvaluation evaluation = answerService.evaluateAnswersForConsultation(consultationId);
        return ResponseEntity.ok(evaluation);
    }
}