package com.quizgame.domain.question.api.v1.dto;

import com.quizgame.domain.answer.api.v1.dto.AnswerDto;

import java.util.List;

public record QuestionDto(
        Long categoryId,
        Long questionId,
        String content,
        int difficulty,
        List<AnswerDto> answers
) {
}
