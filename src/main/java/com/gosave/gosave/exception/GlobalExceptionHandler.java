package com.gosave.gosave.exception;

import com.gosave.gosave.utils.ExceptionApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionApiResponse> userException(UserException userException){
        return new ResponseEntity<>(ExceptionApiResponse
                .builder()
                .data(userException.getMessage())
                .isSuccessful(false)
                .build(), HttpStatus.BAD_REQUEST);

    }
}
