package com.quizgame.global.response;

import com.quizgame.global.code.SystemMessageCode;
import lombok.Builder;

@Builder
public record CommonResponse(
        int code,
        String message
) {
    public static CommonResponse from(SystemMessageCode systemMessageCode) {
        return CommonResponse.builder()
                .code(systemMessageCode.getCode())
                .message(systemMessageCode.getMessage())
                .build();
    }

    public static CommonResponse of(SystemMessageCode systemMessageCode, String message) {
        return CommonResponse.builder()
                .code(systemMessageCode.getCode())
                .message(message)
                .build();
    }
}
