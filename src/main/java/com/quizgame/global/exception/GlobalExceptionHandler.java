package com.quizgame.global.exception;


import com.quizgame.global.code.SystemMessageCode;
import com.quizgame.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 예외 (우리가 만든 것들)
    @ExceptionHandler(QuizGameException.class)
    public ResponseEntity<ErrorResponse> handleQuizGameException(QuizGameException e) {
        SystemMessageCode systemMessageCode = e.getSystemMessageCode();

        ErrorResponse response = ErrorResponse.of(systemMessageCode, e.getMessage());
        return ResponseEntity
                .status(systemMessageCode.getStatus())
                .body(response);
    }

    // @Valid 검증 실패 등
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        SystemMessageCode systemMessageCode = SystemMessageCode.INVALID_INPUT_VALUE;

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .orElse(systemMessageCode.getMessage());

        ErrorResponse response = ErrorResponse.of(systemMessageCode, message);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 그 밖의 예상 못한 예외들
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        SystemMessageCode systemMessageCode = SystemMessageCode.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.from(systemMessageCode);

        // TODO: 여기서 로그 남기면 좋음 (logger.error 등)
        e.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}