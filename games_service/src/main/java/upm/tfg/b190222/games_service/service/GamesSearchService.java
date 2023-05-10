package upm.tfg.b190222.games_service.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import upm.tfg.b190222.games_service.info.GameInfo;
import upm.tfg.b190222.games_service.response.GameResponse;

@Service
public class GamesSearchService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<GameResponse> findGames(GameInfo gameInfo){
        String nombre = gameInfo.getNombre();
        String desarrolladora = gameInfo.getDesarrolladora();
        String plataforma1 = gameInfo.getPlataforma1();
        String plataforma2 = gameInfo.getPlataforma2();
        String plataforma3 = gameInfo.getPlataforma3();
        String genero1 = gameInfo.getGenero1();
        String genero2 = gameInfo.getGenero2();
        String genero3 = gameInfo.getGenero3();
        String notaMediaIni = gameInfo.getNotaMediaIni();
        String notaMediaFin = gameInfo.getNotaMediaFin();
        String fechaLanIni = gameInfo.getFechaLanIni();
        String fechaLanFin = gameInfo.getNotaMediaFin();
        String order = gameInfo.getOrder();
        

        if(nombre == null && plataforma1 == null && plataforma2 == null && plataforma3 == null && desarrolladora == null 
        && genero1 == null && genero2 == null && genero3 == null && notaMediaIni == null && notaMediaFin == null 
        && fechaLanIni == null && fechaLanFin == null && order == null){
            
            return new ResponseEntity<GameResponse>(new GameResponse("MISSING_DATA", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }  
        if(notaMediaIni != null && (Integer.valueOf(notaMediaIni) < 1 || Integer.valueOf(notaMediaIni) < 10)){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_NOTEINI_VALUE", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(notaMediaFin != null && (Integer.valueOf(notaMediaFin) < 1 || Integer.valueOf(notaMediaFin) < 10)){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_NOTEFIN_VALUE", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if((notaMediaIni == null && notaMediaFin != null) || (notaMediaIni != null && notaMediaFin == null)){
            return new ResponseEntity<GameResponse>(new GameResponse("MISSING_NOTE", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(notaMediaIni != null && notaMediaFin != null && Integer.valueOf(notaMediaIni) > Integer.valueOf(notaMediaFin)){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_NOTE_ORDER", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if((fechaLanIni == null && fechaLanFin != null) || (fechaLanIni != null && fechaLanFin == null)){
            return new ResponseEntity<GameResponse>(new GameResponse("MISSING_DATE", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(fechaLanIni != null && fechaLanFin != null && fechaLanIni.compareTo(fechaLanFin) > 0){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_DATE_ORDER", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }




        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Game> cq = cb.createQuery(Game.class);
            Root<Game> games = cq.from(Game.class);

            List<Predicate> predicateList = new ArrayList<>();

            if(nombre != null){
                predicateList.add(cb.equal(games.get("nombre"), nombre));
            }     
            if(plataforma1 != null){
                predicateList.add(cb.or(cb.equal(games.get("plataforma1"), plataforma1), cb.equal(games.get("plataforma2"), plataforma1), cb.equal(games.get("plataforma3"), plataforma1)));
            } 
            if(plataforma2 != null){
                predicateList.add(cb.or(cb.equal(games.get("plataforma1"), plataforma2), cb.equal(games.get("plataforma2"), plataforma2), cb.equal(games.get("plataforma3"), plataforma2)));
            } 
            if(plataforma3 != null){
                predicateList.add(cb.or(cb.equal(games.get("plataforma1"), plataforma3), cb.equal(games.get("plataforma2"), plataforma3), cb.equal(games.get("plataforma3"), plataforma3)));
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


            if(order == null || order.equals("Fecha lanzamiento descendente")){
                cq.select(games).where(predicates).orderBy(cb.desc(games.get("fechaLanzamiento")));
            }
            else if(order.equals("Fecha lanzamiento ascendente")){
                cq.select(games).where(predicates).orderBy(cb.asc(games.get("fechaLanzamiento")));
            }
            else if(order.equals("Nombre ascendente")){
                cq.select(games).where(predicates).orderBy(cb.asc(games.get("nombre")));
            }
            else if(order.equals("Nombre descendente")){
                cq.select(games).where(predicates).orderBy(cb.desc(games.get("nombre")));
            }
            else if(order.equals("Nota media ascendente")){
                cq.select(games).where(predicates).orderBy(cb.asc(games.get("notaMedia")));
            }
            else if(order.equals("Nota media descendente")){
                cq.select(games).where(predicates).orderBy(cb.desc(games.get("notaMedia")));
            } else {
                cq.select(games).where(predicates).orderBy(cb.desc(games.get("notaMedia")));
            }

            List<Game> result = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();

            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            df.applyPattern("##.#");
            df.setRoundingMode(RoundingMode.DOWN);
            for(Game game: result){
                game.setNotaMedia(Float.parseFloat(df.format(game.getNotaMedia())));
            }

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

            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            df.applyPattern("##.#");
            df.setRoundingMode(RoundingMode.DOWN);
            for(Game game: result){
                game.setNotaMedia(Float.parseFloat(df.format(game.getNotaMedia())));
            }

            return new ResponseEntity<GameResponse>(new GameResponse("OK", new Game(), result), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<GameResponse> findGameById(String idGame){
        if(idGame.isBlank() || idGame.length() > 75){
            return new ResponseEntity<GameResponse>(new GameResponse("BAD_GAME_LENGTH", new Game(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }

        try{
            Game result = entityManager.find(Game.class, idGame, LockModeType.PESSIMISTIC_READ);

            return new ResponseEntity<GameResponse>(new GameResponse("OK", result, new ArrayList<>()), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<GameResponse>(new GameResponse("ERROR", new Game(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
