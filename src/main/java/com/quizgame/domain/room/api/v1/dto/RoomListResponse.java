package com.quizgame.domain.room.api.v1.dto;

import java.time.LocalDateTime;

public record RoomListResponse(
        Long roomId,
        String categoryName,
        String status,
        Integer currentPlayer,
        Integer maxPlayer,
        LocalDateTime createdAt
) {}