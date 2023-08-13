package com.engeto.ProjektRegistracnisystem.exceptions;
import com.engeto.ProjektRegistracnisystem.settings.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse> handleInvalidPersonIDException(UserException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(
                false,"User could not be added."));
    }
}
