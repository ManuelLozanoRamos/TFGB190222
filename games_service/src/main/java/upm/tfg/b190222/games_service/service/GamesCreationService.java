package upm.tfg.b190222.games_service.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.response.GameResponse;

@Service
public class GamesCreationService {

    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public ResponseEntity<GameResponse> createGame(Game game){
        String nombre = game.getNombre();
        String desarrolladora = game.getDesarrolladora();
        String plataforma1 = game.getPlataforma1();
        String plataforma2 = game.getPlataforma2();
        String plataforma3 = game.getPlataforma3();
        String genero1 = game.getGenero1();
        String genero2 = game.getGenero2();
        String genero3 = game.getGenero3();
        LocalDate fechaLanzamiento = game.getFechaLanzamiento();

        
        if(nombre == null || desarrolladora == null || plataforma1 == null || genero1 == null || fechaLanzamiento == null){
            return new ResponseEntity<GameResponse>(new GameResponse("MISSING_DATA", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(nombre.isBlank() || nombre.length() > 75){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_GAME_LENGTH", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(desarrolladora.isBlank() || desarrolladora.length() > 50){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_DEVELOPER_LENGTH", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if((plataforma1.isBlank() || plataforma1.length() > 40) || (plataforma2 != null && (plataforma2.isBlank() || plataforma2.length() > 40)) || (plataforma3 != null && (plataforma3.isBlank() || plataforma3.length() > 40))){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_PLATFORM_LENGTH", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(plataforma3 != null && plataforma2 == null){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_PLATFORM_STRUCTURE", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);  
        }
        if((genero1.isBlank() || genero1.length() > 25) || (genero2 != null && (genero2.isBlank() || genero2.length() > 25)) || (genero3 != null && (genero3.isBlank() || genero3.length() > 25))){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_GENRE_LENGTH", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(genero3 != null && genero2 == null){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_GENRE_STRUCTURE", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }

        try{
            Game g = entityManager.find(Game.class, nombre, LockModeType.PESSIMISTIC_READ);
        
            if(g != null) return new ResponseEntity<GameResponse>(new GameResponse("EXISTS", new Game(), new ArrayList<>()), HttpStatus.OK);

            game.setFechaRegistro(LocalDate.now());
            entityManager.persist(game);

            return new ResponseEntity<GameResponse>(new GameResponse("OK", new Game(), new ArrayList<>()), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }
}
