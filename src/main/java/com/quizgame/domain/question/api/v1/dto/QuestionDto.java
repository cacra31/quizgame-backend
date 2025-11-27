package com.quizgame.domain.question.api.v1.dto;

import com.quizgame.domain.answer.api.v1.dto.AnswerDto;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestionDto(
        Long categoryId,
        Long questionId,
        String content,
        int difficulty,
        List<AnswerDto> answers
) {
}
