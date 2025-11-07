package com.quizgame.domain.category.api.v1.dto;

import com.quizgame.domain.category.domain.Category;
import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        String name
) {
    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}