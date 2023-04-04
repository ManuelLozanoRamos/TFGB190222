package upm.tfg.b190222.reviews_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import upm.tfg.b190222.reviews_service.response.SearchByIdResponse;
import upm.tfg.b190222.reviews_service.response.SearchResponse;
import upm.tfg.b190222.reviews_service.service.ReviewsSearchService;


@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ReviewsSearchController{

    @Autowired
    ReviewsSearchService reviewsSearchService;

    @GetMapping(value="/reviews")
    public SearchResponse reviewsSearch(@RequestParam String juego, @RequestParam String username){
        return reviewsSearchService.findReviews(juego, username);
    }

    @GetMapping(value="/reviews/{idReview}")
    public SearchByIdResponse reviewsSearchById(@PathVariable Integer idReview){
        return reviewsSearchService.findReviewById(idReview);
    }
}