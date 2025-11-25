package com.quizgame.domain.game.api.v1.dto;

import lombok.Builder;

@Builder
public record GameRedisVo(
        Long gameId,
        Long roomId,
        String questionId,
        Long userUuId,
        int round,
        String userAnswer
) {
}
