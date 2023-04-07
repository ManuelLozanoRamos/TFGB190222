package upm.tfg.b190222.games_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
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
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Game> cq = cb.createQuery(Game.class);
            Root<Game> games = cq.from(Game.class); 

            Predicate p = cb.equal(games.get("nombre"), idGame);

            cq.select(games).where(p);

            Game g = entityManager.createQuery(cq).getSingleResult();

            entityManager.remove(g);

            return "OK";
        } catch (Exception e){
            return "ERROR";
        }
    }
}
