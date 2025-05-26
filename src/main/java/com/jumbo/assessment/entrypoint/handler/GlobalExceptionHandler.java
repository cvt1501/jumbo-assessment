package com.jumbo.assessment.entrypoint.handler;

import com.jumbo.assessment.entrypoint.dto.DefaultResponse;
import com.jumbo.assessment.infrastructure.exception.LoadStoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoadStoreException.class)
    public ResponseEntity<DefaultResponse<?>> handleStoreLoadException(LoadStoreException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DefaultResponse<>("Internal server error", null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<DefaultResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponse<>("Invalid parameter: " + ex.getName(), "Expected a number"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<DefaultResponse<String>> handleMissingParam(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponse<>("Missing required parameter: " + ex.getParameterName(), "Please provide the required query parameter."));
    }
}
