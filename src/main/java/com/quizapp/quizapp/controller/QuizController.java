package com.quizapp.quizapp.controller;

import com.quizapp.quizapp.model.Question;
import com.quizapp.quizapp.model.QuizSession;
import com.quizapp.quizapp.repository.QuestionRepository;
import com.quizapp.quizapp.repository.QuizSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSessionRepository quizSessionRepository;

    @PostMapping("/start")
    public ResponseEntity<String> startQuiz(@RequestParam Long userId) {
        QuizSession session = new QuizSession();
        session.setUserId(userId);
        session.setTotalQuestions(0);
        session.setCorrectAnswers(0);
        quizSessionRepository.save(session);
        return ResponseEntity.ok("Quiz started for user ID: " + userId);
    }

    @GetMapping("/question")
    public ResponseEntity<Question> getRandomQuestion() {
        Question question = questionRepository.findRandomQuestion();
        return ResponseEntity.ok(question);
    }
    @PostMapping("/answer")
    public ResponseEntity<String> submitAnswer(@RequestParam Long userId, @RequestParam Long questionId, @RequestParam String selectedOption) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        Optional<QuizSession> sessionOpt = quizSessionRepository.findByUserId(userId);

        if (questionOpt.isPresent() && sessionOpt.isPresent()) {
            Question question = questionOpt.get();
            QuizSession session = sessionOpt.get();

            session.setTotalQuestions(session.getTotalQuestions() + 1);
            if (question.getCorrectOption().equals(selectedOption)) {
                session.setCorrectAnswers(session.getCorrectAnswers() + 1);
            }
            quizSessionRepository.save(session);
            return ResponseEntity.ok("Answer submitted successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid question or user.");
    }

    @GetMapping("/results")
    public ResponseEntity<QuizSession> getResults(@RequestParam Long userId) {
        Optional<QuizSession> sessionOpt = quizSessionRepository.findByUserId(userId);
        return sessionOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }
}
