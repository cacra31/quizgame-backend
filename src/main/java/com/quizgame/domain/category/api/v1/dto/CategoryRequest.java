package com.quizgame.domain.category.api.v1.dto;

import lombok.Builder;

@Builder
public record CategoryRequest(
        String name
) {
}