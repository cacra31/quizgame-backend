package com.quizgame.global.exception;

import lombok.Getter;

@Getter
public class QuizGameException extends RuntimeException {

    private final ErrorCode errorCode;

    public QuizGameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public QuizGameException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}