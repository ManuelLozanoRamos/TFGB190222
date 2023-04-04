package upm.tfg.b190222.reviews_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.reviews_service.entity.Review;

@Service
public class DeleteReviewService {

    @Autowired
    EntityManager entityManager;
    
    @Transactional
    public String deleteReview(int idReview){
        try{
           CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviews = cq.from(Review.class); 

            Predicate p = cb.equal(reviews.get("idReview"), idReview);

            cq.select(reviews).where(p);

            Review v = entityManager.createQuery(cq).getSingleResult();

            entityManager.remove(v);

            return "OK";
        } catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }
}
