package upm.tfg.b190222.reviews_service.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.reviews_service.entity.Review;
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

    @GetMapping(value="/reviews")
    public ResponseEntity<ReviewResponse> reviewsSearch(@RequestParam(required = false) String videojuego, @RequestParam(required = false) String username,
                                        @RequestParam(required = false) String notaIni, @RequestParam(required = false) String notaFin, 
                                        @RequestParam(required = false) String fechaRegIni, @RequestParam(required = false) String fechaRegFin, 
                                        @RequestParam(required = false) String order, HttpServletRequest request){
        
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
                                    
            if(!token.contains("USER_SESSION") || !userValidationService.validate(token).equals("VALID_ADMIN")){
                return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<Review>()), HttpStatus.FORBIDDEN);
            }                      
        } else {
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<Review>()), HttpStatus.FORBIDDEN);
        }
        
        try{
            return reviewsSearchService.findReviews(videojuego, username, notaIni, notaFin, fechaRegIni, fechaRegFin, order);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<Review>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/reviews/{idReview}")
    public ResponseEntity<ReviewResponse> reviewsSearchById(@PathVariable Integer idReview, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            if(!token.contains("USER_SESSION") || !userValidationService.validate(token).equals("VALID_ADMIN")){
                return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.FORBIDDEN);
            }

        } else {
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.FORBIDDEN);
        }

        try{
            return reviewsSearchService.findReviewById(idReview);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}