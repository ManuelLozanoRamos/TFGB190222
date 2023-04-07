import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { Review } from '../review';
import { ReviewService } from '../review.service';
import { ReviewInfo } from '../reviewInfo';

@Component({
  selector: 'app-form-reviews',
  templateUrl: './form-reviews.component.html',
  styleUrls: ['./form-reviews.component.css']
})
export class FormReviewsComponent implements OnInit{
  username:string;
  review:Review;
  reviewInfo:ReviewInfo;
  editOrCreate:string;

  constructor(private appComponent:AppComponent, private reviewService:ReviewService, 
              private router:Router, private activatedRoute:ActivatedRoute){
    this.review = new Review();
    this.username = appComponent.username;
    this.reviewInfo = new ReviewInfo();
    this.editOrCreate = 'create';
  }
  ngOnInit(): void {
    this.cargar();
  }

  cargar(){
    this.activatedRoute.params.subscribe(
      //Comprobar si hay errores y demás
      r => {
        let id=r['id'];
        let game=r['game'];
        if(id){
          this.reviewService.getReviewById(id).subscribe(
            resp =>{
              this.review = resp.review;
              this.editOrCreate = 'edit';
            } 
          );
        } else if(game){
          this.review.videojuego = game;
        }
      }
    );
  }

  createReview(){
    if(this.editOrCreate == 'edit'){
      this.reviewInfo.comentario = this.review.comentario;
      this.reviewInfo.titulo = this.review.titulo;
      this.reviewInfo.nota = this.review.nota;
      this.reviewService.editReview(this.review.idReview, this.reviewInfo).subscribe(
      //Comprobar si r.response tambien es EXISTS o ERROR y las validaciones y mostrar mensajes de error en consecuencia
        r =>{
          if(r.response == 'OK'){
            this.router.navigate(['/users/'+this.username+'/reviews']);
          }
        }
      );
    } else {
      this.review.fechaRegistro = new Date();
      this.review.username = this.username;
      this.reviewService.createReview(this.review).subscribe(
      //Comprobar si r.response tambien es EXISTS o ERROR y las validaciones y mostrar mensajes de error en consecuencia
        r =>{
          if(r.response == 'OK'){
            this.router.navigate(['/games/'+this.review.videojuego+'/reviews']);
          }
        }
      );
    }
  }
}
