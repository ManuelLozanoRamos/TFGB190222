package upm.tfg.b190222.reviews_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.response.CreationResponse;
import upm.tfg.b190222.reviews_service.service.ReviewsCreationService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ReviewsCreationController {

    @Autowired
    ReviewsCreationService reviewsCreationService;
    
    @PostMapping(value="/reviews")
    public CreationResponse reviewsCreation(@RequestBody Review review){
        try{
            return new CreationResponse(reviewsCreationService.createReview(review));
        } catch(Exception e){
            return new CreationResponse("ERROR");
        }
    }
}
