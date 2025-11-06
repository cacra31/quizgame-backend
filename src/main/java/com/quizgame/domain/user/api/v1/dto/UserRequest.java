package com.quizgame.domain.user.api.v1.dto;

import lombok.Builder;

@Builder
public record UserRequest(
        String userId,
        String password,
        String name,
        String type
) {}