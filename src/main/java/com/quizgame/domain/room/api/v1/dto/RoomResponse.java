package com.quizgame.domain.room.api.v1.dto;

import com.quizgame.domain.user.api.v1.dto.UserDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record RoomResponse(
        Long roomId,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt,
        List<UserDto> users
) {
}