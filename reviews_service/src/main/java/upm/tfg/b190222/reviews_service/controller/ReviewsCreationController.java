package upm.tfg.b190222.reviews_service.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.response.ReviewResponse;
import upm.tfg.b190222.reviews_service.service.ReviewsCreationService;
import upm.tfg.b190222.reviews_service.service.UserValidationService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ReviewsCreationController {

    @Autowired
    ReviewsCreationService reviewsCreationService;

    @Autowired
    UserValidationService userValidationService;
    
    @PostMapping(value="/reviews")
    public ResponseEntity<ReviewResponse> reviewsCreation(@RequestBody Review review, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            if(!token.contains("USER_SESSION") || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.FORBIDDEN);
        }

        try{
            return reviewsCreationService.createReview(review);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
