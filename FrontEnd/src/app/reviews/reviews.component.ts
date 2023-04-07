import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppComponent } from '../app.component';
import { Review } from './review';
import { ReviewService } from './review.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit{
  userReview:Review;
  userHasReview:boolean;
  finished:boolean;
  reviews:Review[];
  game:string;

  constructor(private reviewService:ReviewService, private appComponent:AppComponent, private activatedRoute:ActivatedRoute){
    this.userReview = new Review;
    this.userHasReview = false;
    this.finished = false;
    this.reviews = [];
    this.game = '';
  }
  
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      r => {
        let id=r['game'];
        if(id){
          this.game = id;
        }
      }
    );
    this.searchByGame();
    this.search();
  }

  search() : void {
    this.reviewService.getReviews(this.game, "any").subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.reviews = r.reviews 
    );
  }

  searchByGame() : void {
    this.reviewService.getReviews(this.game, this.appComponent.username).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r =>{
        if(r.reviews.length != 0){
          this.userReview = r.reviews[0];
          this.userHasReview = true;
        } 
        this.finished = true;
      } 
    );
  }
}
