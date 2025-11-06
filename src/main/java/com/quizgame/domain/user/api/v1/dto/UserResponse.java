package com.quizgame.domain.user.api.v1.dto;

import com.quizgame.domain.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String userId,
        String name,
        String type
) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .type(user.getType())
                .build();
    }
}