package upm.tfg.b190222.games_service.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.games_service.entity.Game;
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
    public SearchResponse gamesSearch(@RequestParam(required = false) String nombre, @RequestParam(required = false) String plataforma, 
        @RequestParam(required = false) String desarrolladora, @RequestParam(required = false) String genero1, @RequestParam(required = false) String genero2,
        @RequestParam(required = false) String genero3, @RequestParam(required = false) String notaMediaIni, @RequestParam(required = false) String notaMediaFin, 
        @RequestParam(required = false) String fechaLanIni, @RequestParam(required = false) String fechaLanFin, @RequestParam(required = false) String order){

        try{
            if(nombre == null && plataforma == null && desarrolladora == null && genero1 == null 
                && genero2 == null && genero3 == null && notaMediaIni == null && notaMediaFin == null 
                && fechaLanIni == null && fechaLanIni == null && order == null){
                    return gamesSearchService.findAllGames();
            }    
            else{
                return gamesSearchService.findGames(nombre, plataforma, desarrolladora, genero1, genero2, 
                                                    genero3, notaMediaIni, notaMediaFin, fechaLanIni, 
                                                    fechaLanFin, order);
            }
        } catch(Exception e){
            return new SearchResponse(new ArrayList<Game>(), "ERROR");
        }
    }


    @GetMapping(value="/games/{idGame}")
    public SearchByIdResponse gamesSearchById(@PathVariable String idGame){
        try{
            return gamesSearchService.findGameById(idGame);
        } catch(Exception e){
            return new SearchByIdResponse(new Game(), "ERROR");
        }
    }
}