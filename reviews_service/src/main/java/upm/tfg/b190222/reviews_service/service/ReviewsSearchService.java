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
import upm.tfg.b190222.reviews_service.response.ReviewResponse;

@Service
public class ReviewsSearchService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<ReviewResponse> findReviews(String videojuego, String username, String notaIni, 
                                      String notaFin,String fechaRegIni, String fechaRegFin, String order){
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
            if(notaIni != null){
                predicateList.add(cb.between(reviews.get("nota"), Integer.valueOf(notaIni), Integer.valueOf(notaFin)));

            }
            if(fechaRegIni != null){
                predicateList.add(cb.between(reviews.get("fechaRegistro"), LocalDate.parse(fechaRegIni), LocalDate.parse(fechaRegFin)));
            } 

            Predicate[] predicates = new Predicate[predicateList.size()];
            for(int i=0; i< predicateList.size(); i++){
                predicates[i] = predicateList.get(i);
            }

            if(order == null || order.equals("Fecha Descendente")){
                cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("fechaRegistro")));
            }
            else if(order.equals("Fecha Ascendente")){
                cq.select(reviews).where(predicates).orderBy(cb.asc(reviews.get("fechaRegistro")));
            }
            else if(order.equals("Juego Ascendente")){
                cq.select(reviews).where(predicates).orderBy(cb.asc(reviews.get("videojuego")));
            }
            else if(order.equals("Juego Descendente")){
                cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("videojuego")));
            }
            else if(order.equals("Nota Ascendente")){
                cq.select(reviews).where(predicates).orderBy(cb.asc(reviews.get("nota")));
            }
            else if(order.equals("Nota Descendente")){
                cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("nota")));
            }

            cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("fechaRegistro")));

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
