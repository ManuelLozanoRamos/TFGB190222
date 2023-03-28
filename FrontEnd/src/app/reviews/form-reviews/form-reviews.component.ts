import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { Review } from '../review';
import { ReviewService } from '../review.service';

@Component({
  selector: 'app-form-reviews',
  templateUrl: './form-reviews.component.html',
  styleUrls: ['./form-reviews.component.css']
})
export class FormReviewsComponent {
  review:Review;

  constructor(private appComponent:AppComponent, private reviewService:ReviewService, private router:Router){
    this.review = new Review();
  }

  createReview(){
    this.review.fecha = new Date();
    this.review.username = this.appComponent.username;
    this.reviewService.createReview(this.review).subscribe(
      //Comprobar si r.response tambien es EXISTS o ERROR y las validaciones y mostrar mensajes de error en consecuencia
      r =>{
        if(r.response == 'OK'){
          this.router.navigate(['/home']);
        }
      }
    );
  }
}
