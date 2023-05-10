package upm.tfg.b190222.tokens_service.controller;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import upm.tfg.b190222.tokens_service.response.TokenResponse;

@ControllerAdvice
public class ErrorsController {
    
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<TokenResponse> handleTypeMismatchException() {
        return new ResponseEntity<TokenResponse>(new TokenResponse("BAD_PATH_VAR", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<TokenResponse> handleHttpMessageNotReadableException() {
        return new ResponseEntity<TokenResponse>(new TokenResponse("NO_BODY", null), HttpStatus.BAD_REQUEST);
    }
}
