package com.quizgame.domain.user.api.v1.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String userId,
        String name
) {
}