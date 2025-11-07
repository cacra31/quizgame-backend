package com.quizgame.domain.code.repository;

import com.quizgame.domain.code.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {
}
