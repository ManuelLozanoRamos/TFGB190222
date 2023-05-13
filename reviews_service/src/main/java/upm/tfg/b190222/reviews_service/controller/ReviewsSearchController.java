package upm.tfg.b190222.reviews_service.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.info.ReviewInfo;
import upm.tfg.b190222.reviews_service.response.ReviewResponse;
import upm.tfg.b190222.reviews_service.service.ReviewsSearchService;
import upm.tfg.b190222.reviews_service.service.UserValidationService;


@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ReviewsSearchController{

    @Autowired
    ReviewsSearchService reviewsSearchService;

    @Autowired
    UserValidationService userValidationService;


    @PostMapping(value="/reviews/filter")
    public ResponseEntity<ReviewResponse> reviewsSearch(@RequestBody ReviewInfo reviewInfo, HttpServletRequest request){
        
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            String[] tokenParts = token.split(":");
                                    
            if(!"USER_SESSION".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<Review>()), HttpStatus.UNAUTHORIZED);
            }                      
        } else {
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<Review>()), HttpStatus.UNAUTHORIZED);
        }
        
        try{
            return reviewsSearchService.findReviews(reviewInfo);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<Review>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // @GetMapping(value="/reviews")
    // public ResponseEntity<ReviewResponse> reviewsSearchAll(HttpServletRequest request){
        
    //     String authorizationHeader = request.getHeader("Authorization");
    //     if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
    //         String token = authorizationHeader.substring(7);
    //         String[] tokenParts = token.split(":");
                                    
    //         if(!"USER_SESSION".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
    //             return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<Review>()), HttpStatus.UNAUTHORIZED);
    //         }                      
    //     } else {
    //         return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<Review>()), HttpStatus.UNAUTHORIZED);
    //     }
        
    //     try{
    //         return reviewsSearchService.findAllReviews();
    //     } catch(Exception e){
    //         return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<Review>()), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping(value="/reviews/{idReview}")
    public ResponseEntity<ReviewResponse> reviewsSearchById(@PathVariable Integer idReview, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            String[] tokenParts = token.split(":");

            if(!"USER_SESSION".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
            }

        } else {
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
        }

        try{
            return reviewsSearchService.findReviewById(idReview);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}