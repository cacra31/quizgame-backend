package com.quizgame.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SystemMessageCode {

    // 성공
    EMPTY_OK(HttpStatus.OK, 20000, ""),
    SAVE_OK(HttpStatus.OK, 20001, "성공적으로 저장 하였습니다."),
    MODIFY_OK(HttpStatus.OK, 20002, "성공적으로 수정 되었습니다."),
    DELETE_OK(HttpStatus.OK, 20003, "성공적으로 삭제 되었습니다."),

    // 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "알 수 없는 서버 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 50001, "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 50002, "검색 결과를 찾을 수 없습니다."),
    USER_ID_DUPLICATE(HttpStatus.CONFLICT, 50003, "이미 존재하는 아이디입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 50004, "로그인이 필요합니다."),
    ALREADY_IN_ROOM(HttpStatus.CONFLICT, 50005, "이미 참여중인 방이 있습니다."),
    ROOM_CREATE_LOCKED(HttpStatus.SERVICE_UNAVAILABLE, 50006, "다른 유저가 방을 생성중입니다."),
    NOT_IN_ROOM(HttpStatus.NOT_FOUND, 50007, "현재 방에 입장한 상태가 아닙니다."),
    ROOM_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, 50008, "최대 입장 가능 인원을 초과했습니다.");

    private final HttpStatus status;
    private final int code;
    private final String message;
}