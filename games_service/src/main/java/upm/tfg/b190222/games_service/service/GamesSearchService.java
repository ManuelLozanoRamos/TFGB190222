package upm.tfg.b190222.games_service.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.response.SearchByIdResponse;
import upm.tfg.b190222.games_service.response.SearchResponse;

@Service
public class GamesSearchService {

    @Autowired
    EntityManager entityManager;

    public SearchResponse findGames(String juego){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Game> cq = cb.createQuery(Game.class);
            Root<Game> games = cq.from(Game.class);

            if(!juego.equals("any")){
                Predicate p = cb.equal(games.get("nombre"), juego);
                cq.select(games).where(p).orderBy(cb.desc(games.get("fecha")));
            } else {
                cq.select(games).orderBy(cb.desc(games.get("fechaRegistro")));
            }        

            List<Game> result = entityManager.createQuery(cq).getResultList();

            return new SearchResponse(result, "OK");
        } catch(Exception e){
            return new SearchResponse(new ArrayList<Game>(), "ERROR");
        }
    }


    public SearchByIdResponse findGameById(String idGame){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Game> cq = cb.createQuery(Game.class);
            Root<Game> games = cq.from(Game.class);
            
            Predicate p = cb.equal(games.get("nombre"), idGame);

            cq.select(games).where(p);

            Game result = entityManager.createQuery(cq).getSingleResult();

            return new SearchByIdResponse(result, "OK");
        } catch(Exception e){
            return new SearchByIdResponse(new Game(), "ERROR");
        }
    }
    
}
