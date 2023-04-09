import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Review } from '../review';
import { ReviewService } from '../review.service';

@Component({
  selector: 'app-user-reviews',
  templateUrl: './user-reviews.component.html',
  styleUrls: ['./user-reviews.component.css']
})
export class UserReviewsComponent {
  username:string;
  reviews:Review[];
  game:string;

  constructor(private reviewService:ReviewService,
              private router:Router, private cookieService:CookieService){
    this.username = this.cookieService.get('token');
    this.reviews = [];
    this.game = '';
    this.searchAll();
  }

  searchByGame() : void {
    this.reviewService.getReviews(this.game, this.username).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.reviews = r.reviews 
    );
  }

  searchAll() : void {
    this.reviewService.getReviews("any", this.username).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.reviews = r.reviews 
    );
  }


  delete(idReview:number) : void{
    this.reviewService.delete(idReview).subscribe(
      r => window.location.reload()
    );
  }

  logout() : void {
    this.cookieService.delete('token');
    this.router.navigate(['/login']);
  }

}
