package upm.tfg.b190222.reviews_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.info.ReviewInfo;

@Service
public class EditReviewService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public String editReview(int idReview, ReviewInfo newReviewInfo){
        try{
            Review review = entityManager.find(Review.class, idReview);

            if(review == null) return "NOT_FOUND";

            review.setTitulo(newReviewInfo.getTitulo());
            review.setComentario(newReviewInfo.getComentario());
            review.setNota(newReviewInfo.getNota());
            review.setFechaRegistro(newReviewInfo.getFechaRegistro());

            entityManager.merge(review);

            return "OK";
        } catch (Exception e){
            return "ERROR";
        }

    }
    
}
