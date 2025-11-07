package com.quizgame.domain.game.api.v1.dto;

import lombok.Builder;

@Builder
public record GameRequest(
        Long roomId,
        Long userId,
        Long questionId,
        int round,
        String userAnswer,
        int orderNo
) {
}