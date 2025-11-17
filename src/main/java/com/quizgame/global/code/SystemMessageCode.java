package com.quizgame.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SystemMessageCode {

    // 200 성공
    EMPTY_OK(HttpStatus.OK, 20000, ""),
    SAVE_OK(HttpStatus.OK, 20001, "성공적으로 저장 하였습니다."),
    MODIFY_OK(HttpStatus.OK, 20002, "성공적으로 수정 되었습니다."),
    DELETE_OK(HttpStatus.OK, 20003, "성공적으로 삭제 되었습니다."),

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "알 수 없는 서버 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 50001, "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 50002, "검색 결과를 찾을 수 없습니다."),
    USER_ID_DUPLICATE(HttpStatus.CONFLICT, 50002, "이미 존재하는 아이디 입니다.");

    private final HttpStatus status;
    private final int code;
    private final String message;
}