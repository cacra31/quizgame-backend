package com.quizgame.domain.question.api.v1.dto;

import com.quizgame.domain.question.domain.Question;
import lombok.Builder;

@Builder
public record QuestionResponse(
        Long id,
        String content,
        String type,
        String difficulty,
        Long categoryId,
        String categoryName
) {
    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .type(question.getType())
                .difficulty(question.getDifficulty())
                .categoryId(question.getCategory().getId())
                .categoryName(question.getCategory().getName())
                .build();
    }
}