package com.quizgame.domain.answer.api.v1.dto;

import lombok.Builder;

@Builder
public record AnswerRedisVo(
        Long answerId,
        Long questionId,
        String answer
) {
}
