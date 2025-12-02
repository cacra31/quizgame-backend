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
        int questionType,
        List<AnswerDto> answers
) {
    public QuestionDto removeAnswers() {
        return QuestionDto.builder()
                .categoryId(this.categoryId)
                .questionId(this.questionId)
                .content(this.content)
                .difficulty(this.difficulty)
                .questionType(this.questionType)
                .answers(List.of())
                .build();
    }

    public QuestionDto removeCorrectYn() {
        return QuestionDto.builder()
                .categoryId(this.categoryId)
                .questionId(this.questionId)
                .content(this.content)
                .difficulty(this.difficulty)
                .questionType(this.questionType)
                .answers(this.answers.stream().map(AnswerDto::removeCorrectYn).toList())
                .build();
    }
}
