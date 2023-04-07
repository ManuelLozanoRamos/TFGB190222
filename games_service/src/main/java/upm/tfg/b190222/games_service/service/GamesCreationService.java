package upm.tfg.b190222.games_service.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
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
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Game> cq = cb.createQuery(Game.class);
            Root<Game> games = cq.from(Game.class);

            Predicate p = cb.equal(games.get("nombre"), game.getNombre());

            cq.select(games).where(p);

            Game g;
            try{
                g = entityManager.createQuery(cq).getSingleResult();
            } catch(NoResultException e){
                g = null;
            }
        
            if(g == null){
                game.setFechaRegistro(LocalDate.now());
                entityManager.persist(game);

                return "OK";
            } else {
                return "EXISTS";
            }  
        } catch(Exception e){
            return "ERROR";
        }  
    }
}
