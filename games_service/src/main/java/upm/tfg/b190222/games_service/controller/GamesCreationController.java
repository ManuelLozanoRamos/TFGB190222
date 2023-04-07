package upm.tfg.b190222.games_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.response.CreationResponse;
import upm.tfg.b190222.games_service.service.GamesCreationService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class GamesCreationController {

    @Autowired
    GamesCreationService gamesCreationService;
    
    @PostMapping(value="/games")
    public CreationResponse gamesCreation(@RequestBody Game game){
        return new CreationResponse(gamesCreationService.createGame(game));
    }
}
