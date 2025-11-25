package com.quizgame.domain.room.api.v1.dto;

import com.quizgame.domain.room.domain.Room;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RoomResponse(
        Long id,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt
) {
    public static RoomResponse fromEntity(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getCategory().getId(),
                room.getCategory().getName(),
                room.getCreatedAt()
        );
    }

    public static RoomResponse fromRedisVo(RoomRedisVo room) {
        return new RoomResponse(
                room.roomId(),
                room.categoryId(),
                room.categoryName(),
                room.createdAt()
        );
    }
}