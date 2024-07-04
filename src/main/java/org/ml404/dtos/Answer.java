package org.ml404.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Answer object representing a response to a question in a consultation")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @Schema(description = "Consultation associated with this answer")
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @Schema(description = "Question answered")
    private Question question;

    @Column(name = "response")
    @Schema(description = "Response to the question")
    private String response;

    // Constructors, getters, and setters

    public Answer() {
    }

    public Answer(Consultation consultation, Question question, String response) {
        this.consultation = consultation;
        this.question = question;
        this.response = response;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

