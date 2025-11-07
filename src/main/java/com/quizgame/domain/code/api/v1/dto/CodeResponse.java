package com.quizgame.domain.code.api.v1.dto;

import com.quizgame.domain.question.domain.Question;
import lombok.Builder;

@Builder
public record CodeResponse(
        Long id,
        String content,
        String type,
        String difficulty,
        Long categoryId,
        String categoryName
) {
    public static CodeResponse from(Question question) {
        return CodeResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .type(question.getType())
                .difficulty(question.getDifficulty())
                .categoryId(question.getCategory().getId())
                .categoryName(question.getCategory().getName())
                .build();
    }
}