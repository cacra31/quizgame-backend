package com.quizgame.domain.answer.api.v1.dto;

import lombok.Builder;

@Builder
public record AnswerDto(
        Long answerId,
        Long questionId,
        String answer,
        boolean correctYn
) {

    public AnswerDto removeCorrectYn(){
        return AnswerDto.builder()
                .answerId(this.answerId)
                .questionId(this.questionId)
                .answer(this.answer)
                .build();
    }

}
