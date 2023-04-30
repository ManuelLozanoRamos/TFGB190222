package upm.tfg.b190222.reviews_service.service;

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
    public ResponseEntity<ReviewResponse> editReview(int idReview, ReviewInfo newReviewInfo){
        try{
            Review review = entityManager.find(Review.class, idReview, LockModeType.PESSIMISTIC_WRITE);

            if(review == null) return new ResponseEntity<ReviewResponse>(new ReviewResponse("NOT_FOUND", new Review(), new ArrayList<>()), HttpStatus.OK);

            review.setTitulo(newReviewInfo.getTitulo());
            review.setComentario(newReviewInfo.getComentario());
            review.setNota(newReviewInfo.getNota());
            review.setFechaRegistro(newReviewInfo.getFechaRegistro());

            entityManager.merge(review);

            return new ResponseEntity<ReviewResponse>(new ReviewResponse("OK", new Review(), new ArrayList<>()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
