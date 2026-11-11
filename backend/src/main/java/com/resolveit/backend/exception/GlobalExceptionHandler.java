package com.resolveit.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleDuplicateEmail(
            EmailAlreadyExistsException exception){
        Map<String,String> response = new LinkedHashMap<>();

        response.put("error", "EMAIL_ALREADY_EXISTS");
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors
            (MethodArgumentNotValidException exception){
        Map<String,String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.putIfAbsent(
                            error.getField(),
                            error.getDefaultMessage()
                    );
                });

        Map<String,String> response = new LinkedHashMap<>();

        response.put("error", "VALIDATION_FAILED");
        response.put("message", "Please check your input");
        response.put("details", errors.toString());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleBadRequest(
            IllegalArgumentException exception) {
        Map<String,String> response = new LinkedHashMap<>();

        response.put("error", "BAD_REQUEST");
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
