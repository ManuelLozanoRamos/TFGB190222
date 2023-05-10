package upm.tfg.b190222.reviews_service.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import upm.tfg.b190222.reviews_service.entity.Review;
import upm.tfg.b190222.reviews_service.response.ReviewResponse;
import upm.tfg.b190222.reviews_service.service.DeleteReviewService;
import upm.tfg.b190222.reviews_service.service.UserValidationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class DeleteReviewController {

    @Autowired
    DeleteReviewService deleteReviewService;

    @Autowired
    UserValidationService userValidationService;
    
    @DeleteMapping(value="/reviews/{idReview}/delete")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable Integer idReview, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String[] tokenParts;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            tokenParts = token.split(":");

            if(!"USER_SESSION".equals(tokenParts[1]) || (!userValidationService.validate(token).equals("VALID") && !userValidationService.validate(token).equals("VALID_ADMIN"))){
                return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("INVALID_TOKEN", new Review(), new ArrayList<>()), HttpStatus.UNAUTHORIZED);
        }

        try{
            return deleteReviewService.deleteReview(idReview, tokenParts[0]);
        } catch(Exception e){
            return new ResponseEntity<ReviewResponse>(new ReviewResponse("ERROR", new Review(), new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
