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
import upm.tfg.b190222.reviews_service.response.ReviewResponse;

@Service
public class DeleteReviewService {

    @Autowired
    EntityManager entityManager;
    
    @Transactional
    public ResponseEntity<ReviewResponse> deleteReview(int idReview, String tokenUser){
        try{
            Review review = entityManager.find(Review.class, idReview, LockModeType.PESSIMISTIC_WRITE);

            if(review == null) return new ResponseEntity<ReviewResponse>(new ReviewResponse("NOT_FOUND", new Review(), new ArrayList<>()), HttpStatus.OK);

            if(!tokenUser.equals(review.getUsername()))  return new ResponseEntity<ReviewResponse>(new ReviewResponse("WRONG_USER", new Review(), new ArrayList<>()), HttpStatus.FORBIDDEN);

            entityManager.remove(review);

            return new ResponseEntity<ReviewResponse>(new ReviewResponse("OK", new Review(), new ArrayList<>()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
