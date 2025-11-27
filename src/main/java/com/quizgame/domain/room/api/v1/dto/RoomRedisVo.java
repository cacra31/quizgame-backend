package com.quizgame.domain.room.api.v1.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record RoomRedisVo(
        Long roomId,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt,
        Set<Long> users,
        int maxPlayers
) {
}
