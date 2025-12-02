package com.quizgame.domain.game.api.v1.dto;

import com.quizgame.domain.question.api.v1.dto.QuestionDto;
import com.quizgame.global.code.GameStatusCode;
import lombok.Builder;

@Builder
public record GameEvent(
        String type,   // "GAME_STARTED", "QUESTION_STARTED", "QUESTION_ENDED", "GAME_FINISHED"
        Long roomId,
        Integer index,
        QuestionDto question
) {
    public static GameEvent gameStart(){
        return GameEvent.builder().type(GameStatusCode.GAME_STARTED.getCode()).build();
    }

    public static GameEvent questionStart(int index, QuestionDto question){
        return GameEvent.builder()
                .type(GameStatusCode.QUESTION_STARTED.getCode())
                .index(index)
                .question(question)
                .build();
    }

    public static GameEvent questionFinish(int index){
        return GameEvent.builder()
                .type(GameStatusCode.QUESTION_FINISHED.getCode())
                .index(index)
                .build();
    }

    public static GameEvent gameFinish(){
        return GameEvent.builder().type(GameStatusCode.GAME_FINISHED.getCode()).build();
    }
}
