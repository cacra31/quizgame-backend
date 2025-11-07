package com.quizgame.domain.code.api.v1.dto;

import lombok.Builder;

@Builder
public record CodeRequest(
        String content,
        String type,
        String difficulty,
        Long categoryId
) {}