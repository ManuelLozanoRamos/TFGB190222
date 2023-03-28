package upm.tfg.b190222.reviews_service.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import upm.tfg.b190222.reviews_service.entity.Review;

@Service
public class ReviewsCreationService {

    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public String createReview(Review review){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviews = cq.from(Review.class);

            Predicate [] p = {
                cb.equal(reviews.get("username"), review.getUsername()),
                cb.equal(reviews.get("videojuego"), review.getVideojuego())
            };
            cq.select(reviews).where(p);

            Review v;
            try{
                v = entityManager.createQuery(cq).getSingleResult();
            } catch(NoResultException e){
                v = null;
            }
        
            if(v == null){
                review.setFecha(LocalDate.now());
                entityManager.persist(review);

                return "OK";
            } else {
                return "EXISTS";
            }  
        } catch(Exception e){
            return "ERROR";
        }  
    }
}
