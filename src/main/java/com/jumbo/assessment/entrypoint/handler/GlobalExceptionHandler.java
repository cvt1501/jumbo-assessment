package com.jumbo.assessment.entrypoint.handler;

import com.jumbo.assessment.infrastructure.exception.StoreLoadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StoreLoadException.class)
    public ResponseEntity<String> handleStoreLoadException(StoreLoadException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
    }
}
