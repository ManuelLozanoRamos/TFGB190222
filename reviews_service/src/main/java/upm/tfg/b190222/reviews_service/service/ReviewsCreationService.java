package upm.tfg.b190222.reviews_service.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.response.ReviewResponse;

@Service
public class ReviewsCreationService {

    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public ResponseEntity<ReviewResponse> createReview(Review review){
        String username = review.getUsername();
        String videojuego = review.getVideojuego();
        String titulo = review.getTitulo();
        String comentario = review.getComentario();
        Integer nota = review.getNota();

        if(username == null || videojuego == null || titulo == null || comentario == null || nota == null){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("MISSING_DATA", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(videojuego.isBlank() || videojuego.length() > 75){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_GAME_LENGTH", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(username.isBlank() || username.length() > 20){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_USERNAME_LENGTH", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(titulo.isBlank() || titulo.length() > 75){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_TITLE_LENGTH", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(titulo.isBlank() || comentario.length() > 500){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_REVIEW_LENGTH", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(nota < 1 || nota > 10){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_GRADE_SCOPE", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }

        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviews = cq.from(Review.class);

            Predicate [] p = {
                cb.equal(reviews.get("username"), username),
                cb.equal(reviews.get("videojuego"), videojuego)
            };
            cq.select(reviews).where(p);

            Review v;
            try{
                v = entityManager.createQuery(cq).setLockMode(LockModeType.PESSIMISTIC_READ).getSingleResult();
            } catch(NoResultException e){
                v = null;
            }
        
            if(v == null){
                review.setFechaRegistro(LocalDate.now());
                entityManager.persist(review);

                return new ResponseEntity<ReviewResponse>(new ReviewResponse("OK", new Review(), new ArrayList<>()), HttpStatus.OK);
            } else {
                return new ResponseEntity<ReviewResponse>(new ReviewResponse("EXISTS", new Review(), new ArrayList<>()), HttpStatus.OK);
            }  
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }
}
