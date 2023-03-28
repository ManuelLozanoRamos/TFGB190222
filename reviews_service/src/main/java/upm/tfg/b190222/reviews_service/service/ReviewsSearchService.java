package upm.tfg.b190222.reviews_service.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.response.SearchResponse;

@Service
public class ReviewsSearchService {

    @Autowired
    EntityManager entityManager;

    public SearchResponse findReviews(String juego, String username){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviews = cq.from(Review.class);

            Predicate p = cb.equal(reviews.get("videojuego"), juego);

            cq.select(reviews).where(p).orderBy(cb.desc(reviews.get("fecha")));

            List<Review> result = entityManager.createQuery(cq).getResultList();

            return new SearchResponse(result, "OK");
        } catch(Exception e){
            return new SearchResponse(new ArrayList<Review>(), "ERROR");
        }
    }
    
}
