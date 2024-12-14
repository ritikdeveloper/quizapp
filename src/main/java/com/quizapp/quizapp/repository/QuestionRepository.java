package com.quizapp.quizapp.repository;

import com.quizapp.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM question ORDER BY random() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();
}