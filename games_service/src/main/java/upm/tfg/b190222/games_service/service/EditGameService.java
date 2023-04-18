package upm.tfg.b190222.games_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.info.GameInfo;

@Service
public class EditGameService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public String editGame(String idGame, GameInfo newGameInfo){
        try{
            Game game = entityManager.find(Game.class, idGame);

            game.setDesarrolladora(newGameInfo.getDesarrolladora());
            game.setPlataforma(newGameInfo.getPlataforma());
            game.setGenero1(newGameInfo.getGenero1());
            game.setGenero2(newGameInfo.getGenero2());
            game.setGenero3(newGameInfo.getGenero3());
            game.setFechaLanzamiento(newGameInfo.getFechaLanzamiento());
            game.setFechaRegistro(newGameInfo.getFechaRegistro());

            entityManager.merge(game);

            return "OK";
        } catch (Exception e){
            return "ERROR";
        }
    }
    
}
