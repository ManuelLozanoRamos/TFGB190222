package upm.tfg.b190222.reviews_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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

            if(!StringUtils.isEmptyOrWhitespaceOnly(newReviewInfo.getTitulo())){
                review.setTitulo(newReviewInfo.getTitulo());
            }
            if(!StringUtils.isEmptyOrWhitespaceOnly(newReviewInfo.getComentario())){
                review.setComentario(newReviewInfo.getComentario());
            }
            if(newReviewInfo.getNota() != -1){
                review.setNota(newReviewInfo.getNota());
            }
            review.setFecha(newReviewInfo.getFecha());

            entityManager.merge(review);

            return "OK";
        } catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
        


    }
    
}
