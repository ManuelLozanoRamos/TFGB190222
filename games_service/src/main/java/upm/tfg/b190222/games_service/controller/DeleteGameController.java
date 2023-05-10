package upm.tfg.b190222.games_service.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.response.GameResponse;
import upm.tfg.b190222.games_service.service.DeleteGameService;
import upm.tfg.b190222.games_service.service.UserValidationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class DeleteGameController {

    @Autowired
    DeleteGameService deleteGameService;

    @Autowired
    UserValidationService userValidationService;
    
    @DeleteMapping(value="/games/{idGame}/delete")
    public ResponseEntity<GameResponse> deleteGame(@PathVariable String idGame, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            String [] tokenParts = token.split(":");

            if(!"USER_SESSION".equals(tokenParts[1]) || !userValidationService.validate(token).equals("VALID_ADMIN")){
                if(userValidationService.validate(token).equals("VALID"))  return new ResponseEntity<GameResponse>(new GameResponse("WRONG_USER", new Game(), new ArrayList<>()), HttpStatus.FORBIDDEN);
                else return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
            }

        } else {
            return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
        }

        try{
            return deleteGameService.deleteGame(idGame);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
