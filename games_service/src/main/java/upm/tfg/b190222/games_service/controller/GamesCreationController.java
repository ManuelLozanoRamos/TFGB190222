package upm.tfg.b190222.games_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.response.CreationResponse;
import upm.tfg.b190222.games_service.service.GamesCreationService;
import upm.tfg.b190222.games_service.service.UserValidationService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class GamesCreationController {

    @Autowired
    GamesCreationService gamesCreationService;

    @Autowired
    UserValidationService userValidationService;
    
    @PostMapping(value="/games")
    public ResponseEntity<CreationResponse> gamesCreation(@RequestBody Game game, @RequestBody String username, @RequestParam String token){
        if(!userValidationService.validate(username, token).equals("OK")){
            return new ResponseEntity<CreationResponse>(new CreationResponse("INVALID_TOKEN"), HttpStatus.FORBIDDEN);
        }

        try{
            return new ResponseEntity<CreationResponse>(new CreationResponse(gamesCreationService.createGame(game)), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<CreationResponse>(new CreationResponse("ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
