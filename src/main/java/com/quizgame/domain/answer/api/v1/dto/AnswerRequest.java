package com.quizgame.domain.answer.api.v1.dto;

import lombok.Builder;

@Builder
public record AnswerRequest(
        Long questionId,
        Long categoryId,
        String answer,
        int orderNo,
        String correctYn
) {
}