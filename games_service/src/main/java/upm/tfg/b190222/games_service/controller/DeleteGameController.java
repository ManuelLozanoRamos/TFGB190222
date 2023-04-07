package upm.tfg.b190222.games_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.games_service.response.DeleteResponse;
import upm.tfg.b190222.games_service.service.DeleteGameService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class DeleteGameController {

    @Autowired
    DeleteGameService deleteGameService;
    
    @DeleteMapping(value="/games/{idGame}/delete")
    public DeleteResponse deleteGame(@PathVariable String idGame){
        return new DeleteResponse(deleteGameService.deleteGame(idGame));
    }
}
