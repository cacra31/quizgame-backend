package com.quizgame.domain.room.api.v1.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RoomListResponse(
        Long roomId,
        Long categoryId,
        String categoryName,
        String status,
        Integer currentPlayer,
        Integer maxPlayer,
        LocalDateTime createdAt
) {}