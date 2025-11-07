package com.quizgame.domain.question.api.v1.dto;

import lombok.Builder;

@Builder
public record QuestionRequest(
        String content,
        String type,
        String difficulty,
        Long categoryId
) {}