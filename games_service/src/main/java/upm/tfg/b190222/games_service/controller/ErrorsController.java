package upm.tfg.b190222.games_service.controller;

import java.util.ArrayList;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.response.GameResponse;

@ControllerAdvice
public class ErrorsController {
    
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<GameResponse> handleMissingPathVariableException() {
        return new ResponseEntity<GameResponse>(new GameResponse("NO_PATH_VAR", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<GameResponse> handleTypeMismatchException() {
        return new ResponseEntity<GameResponse>(new GameResponse("BAD_PATH_VAR", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GameResponse> handleHttpMessageNotReadableException() {
        return new ResponseEntity<GameResponse>(new GameResponse("NO_BODY", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
    }
}
