import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { Review } from './review';
import { ReviewService } from './review.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit{
  reviews:Review[];
  game:string;

  constructor(private reviewService:ReviewService, private appComponent:AppComponent){
    this.reviews = [];
    this.game = '';
  }
  
  ngOnInit(): void {
  }

  search() : void {
    this.reviewService.getReviews(this.game, "any").subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.reviews = r.reviews 
    );
  }
}
