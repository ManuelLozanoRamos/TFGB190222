package upm.tfg.b190222.games_service.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.info.GameInfo;
import upm.tfg.b190222.games_service.response.GameResponse;
import upm.tfg.b190222.games_service.service.GamesSearchService;
import upm.tfg.b190222.games_service.service.UserValidationService;


@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class GamesSearchController{

    @Autowired
    GamesSearchService gamesSearchService;

    @Autowired
    UserValidationService userValidationService;



    @PostMapping(value="/games/filter")
    public ResponseEntity<GameResponse> gamesSearch(@RequestBody GameInfo gameInfo, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            String [] tokenParts = token.split(":");
    
            if(!"USER_SESSION".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
            }
    
        } else {
            return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
        }

        try{    
            return gamesSearchService.findGames(gameInfo);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/games")
    public ResponseEntity<GameResponse> gamesSearchAll(@RequestParam(required = false) String nombre, @RequestParam(required = false) String plataforma1, 
        @RequestParam(required = false) String plataforma2, @RequestParam(required = false) String plataforma3, @RequestParam(required = false) String desarrolladora, 
        @RequestParam(required = false) String genero1, @RequestParam(required = false) String genero2, @RequestParam(required = false) String genero3, 
        @RequestParam(required = false) String notaMediaIni, @RequestParam(required = false) String notaMediaFin, @RequestParam(required = false) String fechaLanIni, 
        @RequestParam(required = false) String fechaLanFin, @RequestParam(required = false) String order, HttpServletRequest request){

        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            String [] tokenParts = token.split(":");
    
            if(!"USER_SESSION".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
            }
    
        } else {
            return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
        }

        try{
            return gamesSearchService.findAllGames(); 
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value="/games/{idGame}")
    public ResponseEntity<GameResponse> gamesSearchById(@PathVariable String idGame, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            String [] tokenParts = token.split(":");
    
            if(!"USER_SESSION".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
            }
    
        } else {
            return new ResponseEntity<GameResponse>(new GameResponse("INVALID_TOKEN", new Game(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
        }

        try{
            return gamesSearchService.findGameById(idGame);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}