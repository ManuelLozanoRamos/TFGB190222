package upm.tfg.b190222.reviews_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.reviews_service.response.DeleteResponse;
import upm.tfg.b190222.reviews_service.service.DeleteReviewService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class DeleteReviewController {

    @Autowired
    DeleteReviewService deleteReviewService;
    
    @DeleteMapping(value="/reviews/{idReview}/delete")
    public DeleteResponse deleteReview(@PathVariable Integer idReview){
        try{
            return new DeleteResponse(deleteReviewService.deleteReview(idReview));
        } catch(Exception e){
            return new DeleteResponse("ERROR");
        }
    }
}
