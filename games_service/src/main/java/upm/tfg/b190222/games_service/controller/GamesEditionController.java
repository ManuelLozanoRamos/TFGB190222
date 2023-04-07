package upm.tfg.b190222.games_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.games_service.info.GameInfo;
import upm.tfg.b190222.games_service.response.EditionResponse;
import upm.tfg.b190222.games_service.service.EditGameService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class GamesEditionController {
    
    @Autowired
    EditGameService editGameService;

    @PutMapping(value="/games/{idGame}/edit")
    public EditionResponse gameEdition(@PathVariable("idGame") String idGame, @RequestBody GameInfo newGameInfo){
        return new EditionResponse(editGameService.editGame(idGame, newGameInfo));
    }
}
