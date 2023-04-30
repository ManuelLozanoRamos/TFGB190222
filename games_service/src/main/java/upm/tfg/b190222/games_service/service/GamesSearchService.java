package upm.tfg.b190222.games_service.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.games_service.entity.Game;
import upm.tfg.b190222.games_service.response.GameResponse;

@Service
public class GamesSearchService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<GameResponse> findGames(String nombre, String plataforma, String desarrolladora,  
     String genero1,  String genero2, String genero3, String notaMediaIni,  
     String notaMediaFin, String fechaLanIni, String fechaLanFin, String order){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Game> cq = cb.createQuery(Game.class);
            Root<Game> games = cq.from(Game.class);

            List<Predicate> predicateList = new ArrayList<>();

            if(nombre != null){
                predicateList.add(cb.equal(games.get("nombre"), nombre));
            }     
            if(plataforma != null){
                predicateList.add(cb.equal(games.get("plataforma"), plataforma));
            } 
            if(desarrolladora != null){
                predicateList.add(cb.equal(games.get("desarrolladora"), desarrolladora));
            } 
            if(genero1 != null){
                predicateList.add(cb.or(cb.equal(games.get("genero1"), genero1), cb.equal(games.get("genero2"), genero1), cb.equal(games.get("genero3"), genero1)));
            }
            if(genero2 != null){
                predicateList.add(cb.or(cb.equal(games.get("genero1"), genero2), cb.equal(games.get("genero2"), genero2), cb.equal(games.get("genero3"), genero2)));
            } 
            if(genero3 != null){
                predicateList.add(cb.or(cb.equal(games.get("genero1"), genero3), cb.equal(games.get("genero2"), genero3), cb.equal(games.get("genero3"), genero3)));
            } 
            if(notaMediaIni != null){
                predicateList.add(cb.between(games.get("notaMedia"), Integer.valueOf(notaMediaIni), Integer.valueOf(notaMediaFin)));

            }
            if(fechaLanIni != null){
                predicateList.add(cb.between(games.get("fechaLanzamiento"), LocalDate.parse(fechaLanIni), LocalDate.parse(fechaLanFin)));
            } 
            
            Predicate[] predicates = new Predicate[predicateList.size()];
            for(int i=0; i< predicateList.size(); i++){
                predicates[i] = predicateList.get(i);
            }

            if(order == null || order.equals("Fecha Descendente")){
                cq.select(games).where(predicates).orderBy(cb.desc(games.get("fechaLanzamiento")));
            }
            else if(order.equals("Fecha Ascendente")){
                cq.select(games).where(predicates).orderBy(cb.asc(games.get("fechaLanzamiento")));
            }
            else if(order.equals("Nombre Ascendente")){
                cq.select(games).where(predicates).orderBy(cb.asc(games.get("nombre")));
            }
            else if(order.equals("Nombre Descendente")){
                cq.select(games).where(predicates).orderBy(cb.desc(games.get("nombre")));
            }
            else if(order.equals("Nota Ascendente")){
                cq.select(games).where(predicates).orderBy(cb.asc(games.get("notaMedia")));
            }
            else if(order.equals("Nota Descendente")){
                cq.select(games).where(predicates).orderBy(cb.desc(games.get("notaMedia")));
            }

            cq.select(games).where(predicates).orderBy(cb.desc(games.get("notaMedia")));

            List<Game> result = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();
    
            return new ResponseEntity<GameResponse>(new GameResponse("OK", new Game(), result), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<GameResponse> findAllGames(){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Game> cq = cb.createQuery(Game.class);
            Root<Game> games = cq.from(Game.class);

            cq.select(games);     

            List<Game> result = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();

            return new ResponseEntity<GameResponse>(new GameResponse("OK", new Game(), result), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<GameResponse> findGameById(String idGame){
        try{
            Game result = entityManager.find(Game.class, idGame, LockModeType.PESSIMISTIC_READ);

            return new ResponseEntity<GameResponse>(new GameResponse("OK", result, new ArrayList<>()), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
