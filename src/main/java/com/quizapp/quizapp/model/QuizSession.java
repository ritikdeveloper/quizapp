package com.quizapp.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;
@Data
@Entity
public class QuizSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int totalQuestions;

    private int correctAnswers;

    @ElementCollection
    private Map<Long, String> userAnswers; // questionId -> answer

    // Getter and Setter for `id`

}
