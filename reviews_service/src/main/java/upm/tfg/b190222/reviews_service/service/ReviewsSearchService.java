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
import upm.tfg.b190222.reviews_service.response.SearchByIdResponse;
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
            
            List<Predicate> predicatesList = new ArrayList<>();

            if(!juego.equals("any")){
                predicatesList.add(cb.equal(reviews.get("videojuego"), juego));
            }
            if(!username.equals("any")){
                predicatesList.add(cb.equal(reviews.get("username"), username));
            }

            Predicate [] predicates;
            if(predicatesList.size() == 2){
                predicates = new Predicate [2];
                predicates[0] = predicatesList.get(0);
                predicates[1] = predicatesList.get(1);

            } else {
                predicates = new Predicate [1];
                predicates[0] = predicatesList.get(0);
            }

            cq.select(reviews).where(predicates).orderBy(cb.desc(reviews.get("fechaRegistro")));

            List<Review> result = entityManager.createQuery(cq).getResultList();

            return new SearchResponse(result, "OK");
        } catch(Exception e){
            return new SearchResponse(new ArrayList<Review>(), "ERROR");
        }
    }


    public SearchByIdResponse findReviewById(int idReview){
        try{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Review> cq = cb.createQuery(Review.class);
            Root<Review> reviews = cq.from(Review.class);
            
            Predicate p = cb.equal(reviews.get("idReview"), idReview);

            cq.select(reviews).where(p);

            Review result = entityManager.createQuery(cq).getSingleResult();

            return new SearchByIdResponse(result, "OK");
        } catch(Exception e){
            return new SearchByIdResponse(new Review(), "ERROR");
        }
    }
    
}
