package com.quizgame.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameStatusCode {

    GAME_STARTED("GAME_STARTED", "게임 시작"),
    QUESTION_STARTED("QUESTION_STARTED", "문제 출제"),
    QUESTION_FINISHED("QUESTION_FINISHED", "문제 제출 마감"),
    GAME_FINISHED("GAME_FINISHED", "게임 종료");

    private final String code;
    private final String desc;
}