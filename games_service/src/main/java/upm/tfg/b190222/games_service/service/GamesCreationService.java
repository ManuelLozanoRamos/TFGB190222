package upm.tfg.b190222.games_service.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.games_service.entity.Game;

@Service
public class GamesCreationService {

    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public String createGame(Game game){
        try{
            Game g = entityManager.find(Game.class, game.getNombre(), LockModeType.PESSIMISTIC_READ);
        
            if(g != null) return "EXISTS";

            game.setFechaRegistro(LocalDate.now());
            entityManager.persist(game);

            return "OK";
        } catch(Exception e){
            return "ERROR";
        }  
    }
}
