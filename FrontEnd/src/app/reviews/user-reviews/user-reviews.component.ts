import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { Review } from '../review';
import { ReviewService } from '../review.service';

@Component({
  selector: 'app-user-reviews',
  templateUrl: './user-reviews.component.html',
  styleUrls: ['./user-reviews.component.css']
})
export class UserReviewsComponent {

  reviews:Review[];
  game:string;

  constructor(private reviewService:ReviewService, private appComponent:AppComponent, private router:Router){
    this.reviews = [];
    this.game = '';
    this.searchAll();
  }

  searchByGame() : void {
    this.reviewService.getReviews(this.game, this.appComponent.username).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.reviews = r.reviews 
    );
  }

  searchAll() : void {
    this.reviewService.getReviews("any", this.appComponent.username).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.reviews = r.reviews 
    );
  }


  delete(idReview:number) : void{
    this.reviewService.delete(idReview).subscribe(
      r => window.location.reload()
    );
  }

}
