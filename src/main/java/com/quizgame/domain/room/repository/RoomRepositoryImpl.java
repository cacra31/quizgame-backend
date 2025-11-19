package com.quizgame.domain.room.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quizgame.domain.category.domain.QCategory;
import com.quizgame.domain.room.api.v1.dto.RoomListResponse;
import com.quizgame.domain.room.domain.QRoom;
import com.quizgame.domain.room.domain.QRoomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {


    private final JPAQueryFactory query;

    @Override
    public List<RoomListResponse> findRoomList() {

        QRoom room = QRoom.room;
        QCategory category = QCategory.category;
        QRoomUser roomUser = QRoomUser.roomUser;

        return query
                .select(Projections.constructor(
                        RoomListResponse.class,
                        room.id,
                        category.name,
                        room.status,
                        roomUser.user.id.countDistinct().intValue(),
                        Expressions.constant(4),
                        room.createdAt
                ))
                .from(category)
                .leftJoin(room).on(category.id.eq(room.category.id).and(room.status.eq("WAITING")))
                .leftJoin(roomUser).on(room.id.eq(roomUser.room.id))
                .groupBy(
                        room.id,
                        category.name,
                        room.status,
                        room.createdAt
                )
                .fetch();
    }
}
