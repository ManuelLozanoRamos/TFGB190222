package upm.tfg.b190222.games_service.service;

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
    public ResponseEntity<GameResponse> editGame(String idGame, GameInfo newGameInfo){
        try{
            Game game = entityManager.find(Game.class, idGame, LockModeType.PESSIMISTIC_WRITE);

            if(game == null) return new ResponseEntity<GameResponse>(new GameResponse("NOT_FOUND", new Game(), new ArrayList<>()), HttpStatus.OK);

            game.setDesarrolladora(newGameInfo.getDesarrolladora());
            game.setPlataforma(newGameInfo.getPlataforma());
            game.setGenero1(newGameInfo.getGenero1());
            game.setGenero2(newGameInfo.getGenero2());
            game.setGenero3(newGameInfo.getGenero3());
            game.setFechaLanzamiento(newGameInfo.getFechaLanzamiento());
            game.setFechaRegistro(newGameInfo.getFechaRegistro());

            entityManager.merge(game);

            return new ResponseEntity<GameResponse>(new GameResponse("OK", new Game(), new ArrayList<>()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
