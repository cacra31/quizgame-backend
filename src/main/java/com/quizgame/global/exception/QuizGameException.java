package com.quizgame.global.exception;

import com.quizgame.global.code.SystemMessageCode;
import lombok.Getter;

@Getter
public class QuizGameException extends RuntimeException {

    private final SystemMessageCode systemMessageCode;

    public QuizGameException(SystemMessageCode systemMessageCode) {
        super(systemMessageCode.getMessage());
        this.systemMessageCode = systemMessageCode;
    }

    public QuizGameException(SystemMessageCode systemMessageCode, String message) {
        super(message);
        this.systemMessageCode = systemMessageCode;
    }
}