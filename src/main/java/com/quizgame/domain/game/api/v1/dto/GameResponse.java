package com.quizgame.domain.game.api.v1.dto;

import com.quizgame.domain.game.domain.Game;
import lombok.Builder;

@Builder
public record GameResponse(
        Long id,
        Long roomId,
        Long userId,
        String userName,
        Long questionId,
        String questionContent,
        int round,
        String userAnswer,
        String correctYn,
        int orderNo
) {
    public static GameResponse from(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .roomId(game.getRoom().getId())
                .userId(game.getUser().getId())
                .userName(game.getUser().getName())
                .questionId(game.getQuestion().getId())
                .questionContent(game.getQuestion().getContent())
                .round(game.getRound())
                .userAnswer(game.getUserAnswer())
                .correctYn(game.getCorrectYn())
                .orderNo(game.getOrderNo())
                .build();
    }
}