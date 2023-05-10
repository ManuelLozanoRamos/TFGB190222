package upm.tfg.b190222.usuarios_service.controller;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import upm.tfg.b190222.usuarios_service.response.UserResponse;

@ControllerAdvice
public class ErrorsController {
    
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<UserResponse> handleTypeMismatchException() {
        return new ResponseEntity<UserResponse>(new UserResponse("BAD_PATH_VAR", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<UserResponse> handleHttpMessageNotReadableException() {
        return new ResponseEntity<UserResponse>(new UserResponse("NO_BODY", null), HttpStatus.BAD_REQUEST);
    }
}
