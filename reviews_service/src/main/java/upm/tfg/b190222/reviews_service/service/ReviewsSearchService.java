package upm.tfg.b190222.reviews_service.service;

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
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.info.ReviewInfo;
import upm.tfg.b190222.reviews_service.response.ReviewResponse;

@Service
public class ReviewsSearchService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<ReviewResponse> findReviews(ReviewInfo reviewInfo){
        String username = reviewInfo.getUsername();
        String videojuego = reviewInfo.getVideojuego();
        String notaMediaIni = reviewInfo.getNotaMediaIni();
        String notaMediaFin = reviewInfo.getNotaMediaFin();
        String fechaRegIni = reviewInfo.getFechaRegIni();
        String fechaRegFin = reviewInfo.getFechaRegFin();
        String order = reviewInfo.getOrder();

        if(username == null && videojuego == null && notaMediaIni == null && notaMediaFin == null 
        && fechaRegIni == null && fechaRegFin == null && order == null){

            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_REQUEST", new Review(), new ArrayList<Review>()), HttpStatus.BAD_REQUEST);
        }
        if(notaMediaIni != null && (Integer.valueOf(notaMediaIni) < 1 || Integer.valueOf(notaMediaIni) < 10)){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_NOTEINI_VALUE", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(notaMediaFin != null && (Integer.valueOf(notaMediaFin) < 1 || Integer.valueOf(notaMediaFin) < 10)){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_NOTEFIN_VALUE", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if((notaMediaIni == null && notaMediaFin != null) || (notaMediaIni != null && notaMediaFin == null)){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("MISSING_NOTE", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(notaMediaIni != null && notaMediaFin != null && Integer.valueOf(notaMediaIni) > Integer.valueOf(notaMediaFin)){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_NOTE_ORDER", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if((fechaRegIni == null && fechaRegFin != null) || (fechaRegIni != null && fechaRegFin == null)){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("MISSING_DATE", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(fechaRegIni != null && fechaRegFin != null && fechaRegIni.compareTo(fechaRegFin) > 0){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_DATE_ORDER", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }


        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviews = cq.from(Review.class);
            
            List<Predicate> predicateList = new ArrayList<>();
            if(videojuego != null){
                predicateList.add(cb.equal(reviews.get("videojuego"), videojuego));
            }
            if(username != null){
                predicateList.add(cb.equal(reviews.get("username"), username));
            }
            if(notaMediaIni != null){
                predicateList.add(cb.between(reviews.get("nota"), Integer.valueOf(notaMediaIni), Integer.valueOf(notaMediaFin)));

            }
            if(fechaRegIni != null){
                predicateList.add(cb.between(reviews.get("fechaRegistro"), LocalDate.parse(fechaRegIni), LocalDate.parse(fechaRegFin)));
            } 

            Predicate[] predicates = new Predicate[predicateList.size()];
            for(int i=0; i< predicateList.size(); i++){
                predicates[i] = predicateList.get(i);
            }


            if(order == null || order.equals("Fecha registro descendente")){
                cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("fechaRegistro")));
            }
            else if(order.equals("Fecha registro ascendente")){
                cq.select(reviews).where(predicates).orderBy(cb.asc(reviews.get("fechaRegistro")));
            }
            else if(order.equals("Nombre juego ascendente")){
                cq.select(reviews).where(predicates).orderBy(cb.asc(reviews.get("videojuego")));
            }
            else if(order.equals("Nombre juego descendente")){
                cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("videojuego")));
            }
            else if(order.equals("Nota ascendente")){
                cq.select(reviews).where(predicates).orderBy(cb.asc(reviews.get("nota")));
            }
            else if(order.equals("Nota descendente")){
                cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("nota")));
            } else {
                cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("fechaRegistro")));
            }

            List<Review> result = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();

            return new ResponseEntity<ReviewResponse>(new ReviewResponse("OK", new Review(), result), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<Review>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<ReviewResponse> findAllReviews(){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> games = cq.from(Review.class);

            cq.select(games);     

            List<Review> result = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();

            return new ResponseEntity<ReviewResponse>(new ReviewResponse("OK", new Review(), result), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<Review>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<ReviewResponse> findReviewById(int idReview){
        try{
            Review result = entityManager.find(Review.class, idReview, LockModeType.PESSIMISTIC_READ);

            return new ResponseEntity<ReviewResponse>(new ReviewResponse("OK", result, new ArrayList<>()), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
