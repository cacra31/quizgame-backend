package com.quizgame.domain.game.api.v1.dto;

import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import lombok.Builder;

@Builder
public record GameEvent(
        String type,   // "GAME_STARTED", "QUESTION_STARTED", "QUESTION_ENDED", "GAME_FINISHED"
        Long roomId,
        Integer index,
        QuestionDto question
) {
}
