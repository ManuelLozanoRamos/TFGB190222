package upm.tfg.b190222.games_service.controller;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.games_service.response.DeleteResponse;
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
    public ResponseEntity<DeleteResponse> deleteGame(@PathVariable String idGame, @RequestParam String username, @RequestParam String token){
        if(!userValidationService.validate(username, token).equals("OK")){
            return new ResponseEntity<DeleteResponse>(new DeleteResponse("INVALID_TOKEN"), HttpStatus.FORBIDDEN);
        }

        try{
            return new ResponseEntity<DeleteResponse>(new DeleteResponse(deleteGameService.deleteGame(idGame)), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<DeleteResponse>(new DeleteResponse("ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
