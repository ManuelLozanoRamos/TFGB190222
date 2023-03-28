package upm.tfg.b190222.reviews_service.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.reviews_service.entity.Review;

@Getter
@Setter
public class SearchResponse {
    
    private List<Review> reviews;
    private String response;

    public SearchResponse(List<Review> reviews, String response){
        this.reviews = reviews;
        this.response = response;
    }

}
