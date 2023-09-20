package com.engeto.backend.exception;

import com.engeto.backend.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static com.engeto.backend.controller.UserController.INVALID_ID;
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse> handleInvalidPersonIDException(UserException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INVALID_ID);
    }
}
