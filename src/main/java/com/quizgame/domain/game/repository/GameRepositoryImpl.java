package com.quizgame.domain.game.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepositoryCustom {

    private final EntityManager em;

}
