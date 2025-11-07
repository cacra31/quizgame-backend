package com.quizgame.domain.game.repository;

import com.quizgame.domain.game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
