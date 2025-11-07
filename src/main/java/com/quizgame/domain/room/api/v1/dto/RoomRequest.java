package com.quizgame.domain.room.api.v1.dto;

import lombok.Builder;

@Builder
public record RoomRequest(
        Long roomId,
        Long categoryId
) {}