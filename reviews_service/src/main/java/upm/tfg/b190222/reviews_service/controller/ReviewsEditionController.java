package upm.tfg.b190222.reviews_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.reviews_service.info.ReviewInfo;
import upm.tfg.b190222.reviews_service.response.EditionResponse;
import upm.tfg.b190222.reviews_service.service.EditReviewService;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ReviewsEditionController {
    
    @Autowired
    EditReviewService editReviewService;

    @PutMapping(value="/reviews/{idReview}/edit")
    public EditionResponse reviewsEdition(@PathVariable("idReview") Integer idReview, @RequestBody ReviewInfo newReviewInfo){
        try{
            return new EditionResponse(editReviewService.editReview(idReview, newReviewInfo));
        } catch(Exception e){
            return new EditionResponse("ERROR");
        }
    }
}
