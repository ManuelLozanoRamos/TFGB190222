import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { Review } from '../review';
import { ReviewService } from '../review.service';

@Component({
  selector: 'app-form-reviews',
  templateUrl: './form-reviews.component.html',
  styleUrls: ['./form-reviews.component.css']
})
export class FormReviewsComponent implements OnInit{
  review:Review;

  constructor(private appComponent:AppComponent, private reviewService:ReviewService, 
              private router:Router, private activatedRoute:ActivatedRoute){
    this.review = new Review();
  }
  ngOnInit(): void {
    this.cargar();
  }

  cargar(){
    this.activatedRoute.params.subscribe(
      //Comprobar si hay errore y demás
      r => {
        let id=r['id'];
        if(id){
          this.reviewService.getReviewById(id).subscribe(
            rev => this.review = rev.review
          );
        }
      }
    );
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
