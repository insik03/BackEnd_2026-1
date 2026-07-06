package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<String> handleNotFound(Exception404 e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception409.class)
    public ResponseEntity<String> handleConflict(Exception409 e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<String> handleBadRequest(Exception400 e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<String> handleUnauthorized(Exception401 e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationFields(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("입력 필드에 null 값이 존재합니다.", HttpStatus.BAD_REQUEST);
    }
}