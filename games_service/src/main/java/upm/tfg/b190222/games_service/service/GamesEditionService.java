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
import upm.tfg.b190222.games_service.info.GameInfo;
import upm.tfg.b190222.games_service.response.GameResponse;

@Service
public class GamesEditionService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<GameResponse> editGame(String idGame, GameInfo gameInfo){
        String desarrolladora = gameInfo.getDesarrolladora();
        String plataforma1 = gameInfo.getPlataforma1();
        String plataforma2 = gameInfo.getPlataforma2();
        String plataforma3 = gameInfo.getPlataforma3();
        String genero1 = gameInfo.getGenero1();
        String genero2 = gameInfo.getGenero2();
        String genero3 = gameInfo.getGenero3();
        LocalDate fechaLanzamiento = gameInfo.getFechaLanzamiento();

        
        if(idGame == null || desarrolladora == null || plataforma1 == null || genero1 == null || fechaLanzamiento == null){
            return new ResponseEntity<GameResponse>(new GameResponse("MISSING_DATA", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(idGame.isBlank() || idGame.length() > 75){
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
            Game game = entityManager.find(Game.class, idGame, LockModeType.PESSIMISTIC_WRITE);

            if(game == null) return new ResponseEntity<GameResponse>(new GameResponse("NOT_FOUND", new Game(), new ArrayList<>()), HttpStatus.OK);

            game.setDesarrolladora(desarrolladora);
            game.setPlataforma1(plataforma1);
            game.setPlataforma2(plataforma2);
            game.setPlataforma3(plataforma3);
            game.setGenero1(genero1);
            game.setGenero2(genero2);
            game.setGenero3(genero3);
            game.setFechaLanzamiento(fechaLanzamiento);
            game.setFechaRegistro(LocalDate.now());

            entityManager.merge(game);

            return new ResponseEntity<GameResponse>(new GameResponse("OK", new Game(), new ArrayList<>()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
