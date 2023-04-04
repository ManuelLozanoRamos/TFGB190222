package upm.tfg.b190222.reviews_service.response;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.reviews_service.entity.Review;

@Getter
@Setter
public class SearchByIdResponse {
    
    private Review review;
    private String response;

    public SearchByIdResponse(Review review, String response){
        this.review = review;
        this.response = response;
    }

}
