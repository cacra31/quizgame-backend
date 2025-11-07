package com.quizgame.global.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        int code,
        String message
) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(message)
                .build();
    }
}