package com.engeto.ProjektRegistracnisystem.exceptions;
import com.engeto.ProjektRegistracnisystem.settings.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.engeto.ProjektRegistracnisystem.controllers.UserController.NOT_CREATED;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse> handleInvalidPersonIDException(UserException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(NOT_CREATED);
    }
}
