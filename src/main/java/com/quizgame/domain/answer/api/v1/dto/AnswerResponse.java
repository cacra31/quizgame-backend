package com.quizgame.domain.answer.api.v1.dto;

import com.quizgame.domain.answer.domain.Answer;
import lombok.Builder;

@Builder
public record AnswerResponse(
        Long id,
        Long questionId,
        String questionContent,
        Long categoryId,
        String categoryName,
        String answer,
        int orderNo,
        String correctYn
) {
    public static AnswerResponse from(Answer answer) {
        return AnswerResponse.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .questionContent(answer.getQuestion().getContent())
                .categoryId(answer.getCategory().getId())
                .categoryName(answer.getCategory().getName())
                .answer(answer.getAnswer())
                .orderNo(answer.getOrderNo())
                .correctYn(answer.getCorrectYn())
                .build();
    }
}