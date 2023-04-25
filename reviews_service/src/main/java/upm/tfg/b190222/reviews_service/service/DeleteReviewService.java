package upm.tfg.b190222.reviews_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.reviews_service.entity.Review;

@Service
public class DeleteReviewService {

    @Autowired
    EntityManager entityManager;
    
    @Transactional
    public String deleteReview(int idReview){
        try{
            Review review = entityManager.find(Review.class, idReview, LockModeType.PESSIMISTIC_WRITE);

            if(review == null) return "NOT_FOUND";

            entityManager.remove(review);

            return "OK";
        } catch (Exception e){
            return "ERROR";
        }
    }
}
