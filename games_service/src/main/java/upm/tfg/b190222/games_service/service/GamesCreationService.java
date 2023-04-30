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
        try{
            Game g = entityManager.find(Game.class, game.getNombre(), LockModeType.PESSIMISTIC_READ);
        
            if(g != null) return new ResponseEntity<GameResponse>(new GameResponse("EXISTS", new Game(), new ArrayList<>()), HttpStatus.OK);

            game.setFechaRegistro(LocalDate.now());
            entityManager.persist(game);

            return new ResponseEntity<GameResponse>(new GameResponse("OK", new Game(), new ArrayList<>()), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }
}
