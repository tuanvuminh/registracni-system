package com.engeto.ProjektRegistracnisystem.exception;

import com.engeto.ProjektRegistracnisystem.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static com.engeto.ProjektRegistracnisystem.controller.UserController.INVALID_ID;
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse> handleInvalidPersonIDException(UserException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INVALID_ID);
    }
}
