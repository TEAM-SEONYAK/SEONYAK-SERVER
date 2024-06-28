package org.sopt.seonyakServer.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 비즈니스 로직에서 발생한 예외 (언체크)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorType> handleBusinessException(CustomException e) {
        log.error("GlobalExceptionHandler catch CustomException : {}", e.getErrorType().getMessage());
        return ResponseEntity
                .status(e.getErrorType().getHttpStatus())
                .body(e.getErrorType());
    }
}
