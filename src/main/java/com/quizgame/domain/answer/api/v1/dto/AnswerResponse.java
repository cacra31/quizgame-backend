package com.quizgame.domain.answer.api.v1.dto;

import com.quizgame.domain.answer.domain.Answer;
import lombok.Builder;

@Builder
public record AnswerResponse(
        Long id,
        Long questionId,
        String questionContent,
        String answer,
        int orderNo,
        String correctYn
) {
    public static AnswerResponse from(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .questionContent(answer.getQuestion().getContent())
                .answer(answer.getAnswer())
                .orderNo(answer.getOrderNo())
                .correctYn(answer.getCorrectYn())
                .build();
    }
}