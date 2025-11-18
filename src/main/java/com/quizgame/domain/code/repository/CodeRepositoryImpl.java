package com.quizgame.domain.code.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quizgame.domain.room.domain.Room;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.quizgame.domain.room.domain.QRoom.room;

@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepositoryCustom {

    private final EntityManager em;

    private final JPAQueryFactory query;

    public List<Room> findAll() {
        return query
                .selectFrom(room)
                .fetch();
    }
}
