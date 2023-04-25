package upm.tfg.b190222.games_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.games_service.entity.Game;

@Service
public class DeleteGameService {

    @Autowired
    EntityManager entityManager;
    
    @Transactional
    public String deleteGame(String idGame){
        try{
            Game game = entityManager.find(Game.class, idGame, LockModeType.PESSIMISTIC_WRITE);

            if(game == null) return "NOT_FOUND";

            entityManager.remove(game);

            return "OK";
        } catch (Exception e){
            return "ERROR";
        }
    }
}
