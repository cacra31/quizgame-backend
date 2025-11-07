package com.quizgame.domain.code.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupCodeRepositoryImpl implements GroupCodeRepositoryCustom {

    private final EntityManager em;

}
