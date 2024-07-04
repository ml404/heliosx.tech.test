package org.ml404.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Schema(description = "Details about the question. Questions are deemed to be immutable (i.e. cannot be updated once submitted).")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "The unique ID of the question")
    private Long id;

    @Column(name = "text", nullable = false)
    @Schema(description = "The text of the question")
    private String text;

    @OneToMany(mappedBy = "question")
    @Schema(description = "The answers associated to the question")
    private List<Answer> answers;

    // Constructors, getters, and setters

    public Question() {
    }

    public Question(String text) {
        this.text = text;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}

