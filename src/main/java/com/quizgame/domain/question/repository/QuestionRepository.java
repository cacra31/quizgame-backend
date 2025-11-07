package com.quizgame.domain.question.repository;

import com.quizgame.domain.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
