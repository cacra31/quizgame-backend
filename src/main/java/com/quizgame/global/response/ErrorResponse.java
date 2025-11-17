package com.quizgame.global.response;

import com.quizgame.global.code.SystemMessageCode;
import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        int code,
        String message
) {
    public static ErrorResponse from(SystemMessageCode systemMessageCode) {
        return ErrorResponse.builder()
                .status(systemMessageCode.getStatus().value())
                .code(systemMessageCode.getCode())
                .message(systemMessageCode.getMessage())
                .build();
    }

    public static ErrorResponse of(SystemMessageCode systemMessageCode, String message) {
        return ErrorResponse.builder()
                .status(systemMessageCode.getStatus().value())
                .code(systemMessageCode.getCode())
                .message(message)
                .build();
    }
}