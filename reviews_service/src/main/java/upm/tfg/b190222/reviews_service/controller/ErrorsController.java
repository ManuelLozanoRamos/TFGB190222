package upm.tfg.b190222.reviews_service.controller;

import java.util.ArrayList;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.response.ReviewResponse;

@ControllerAdvice
public class ErrorsController {
    
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ReviewResponse> handleMissingPathVariableException() {
        return new ResponseEntity<ReviewResponse>(new ReviewResponse("NO_PATH_VAR", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ReviewResponse> handleTypeMismatchException() {
        return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_PATH_VAR", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ReviewResponse> handleHttpMessageNotReadableException() {
        return new ResponseEntity<ReviewResponse>(new ReviewResponse("NO_BODY", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
    }
}
