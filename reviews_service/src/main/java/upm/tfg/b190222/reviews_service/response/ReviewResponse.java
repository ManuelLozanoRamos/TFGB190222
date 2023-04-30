package upm.tfg.b190222.reviews_service.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.reviews_service.entity.Review;

@Getter
@Setter
public class ReviewResponse {
    
    private String response;
    private Review review;
    private List<Review> reviews;

    public ReviewResponse(String response, Review review, List<Review> reviews){
        this.response = response;
        this.review = review;
        this.reviews = reviews;
    }

}
