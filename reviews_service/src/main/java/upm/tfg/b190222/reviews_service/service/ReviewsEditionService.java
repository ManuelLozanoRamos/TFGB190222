package upm.tfg.b190222.reviews_service.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.info.ReviewInfo;
import upm.tfg.b190222.reviews_service.response.ReviewResponse;

@Service
public class ReviewsEditionService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public ResponseEntity<ReviewResponse> editReview(int idReview, ReviewInfo reviewInfo, String tokenUser){
        String username = reviewInfo.getUsername();
        String videojuego = reviewInfo.getVideojuego();
        String titulo = reviewInfo.getTitulo();
        String comentario = reviewInfo.getComentario();
        Integer nota = reviewInfo.getNota();

        if(videojuego == null ||username == null || titulo == null || comentario == null || nota == null){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_REQUEST", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
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
        if(comentario.isBlank() || comentario.length() > 500){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_REVIEW_LENGTH", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }
        if(nota < 1 || nota > 10){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("BAD_GRADE_SCOPE", new Review(), new ArrayList<>()), HttpStatus.BAD_REQUEST);
        }

        try{
            Review review = entityManager.find(Review.class, idReview, LockModeType.PESSIMISTIC_WRITE);

            if(review == null) return new ResponseEntity<ReviewResponse>(new ReviewResponse("NOT_FOUND", new Review(), new ArrayList<>()), HttpStatus.OK);

            if(!tokenUser.equals(review.getUsername()))  return new ResponseEntity<ReviewResponse>(new ReviewResponse("WRONG_USER", new Review(), new ArrayList<>()), HttpStatus.FORBIDDEN);

            review.setTitulo(titulo);
            review.setComentario(comentario);
            review.setNota(nota);
            review.setFechaRegistro(LocalDate.now());

            entityManager.merge(review);

            return new ResponseEntity<ReviewResponse>(new ReviewResponse("OK", new Review(), new ArrayList<>()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
