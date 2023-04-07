package upm.tfg.b190222.games_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.games_service.response.SearchByIdResponse;
import upm.tfg.b190222.games_service.response.SearchResponse;
import upm.tfg.b190222.games_service.service.GamesSearchService;


@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class GamesSearchController{

    @Autowired
    GamesSearchService gamesSearchService;

    @GetMapping(value="/games")
    public SearchResponse gamesSearch(@RequestParam String juego){
        return gamesSearchService.findGames(juego);
    }

    @GetMapping(value="/games/{idGame}")
    public SearchByIdResponse gamesSearchById(@PathVariable String idGame){
        return gamesSearchService.findGameById(idGame);
    }
}