package com.quizgame.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "알 수 없는 서버 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 50001, "잘못된 요청입니다."),

    // 도메인별 예시
    NOT_FOUND(HttpStatus.NOT_FOUND, 50002, "검색 결과를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}