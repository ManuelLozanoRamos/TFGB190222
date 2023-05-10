import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Review } from '../review';
import { ReviewService } from '../review.service';
import { ReviewInfo } from '../reviewInfo';
import { MenuItem } from 'primeng/api';

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

  items!:MenuItem[];
  itemsUser!:MenuItem[];

  constructor(private reviewService:ReviewService, private cookieService:CookieService,
              private router:Router, private activatedRoute:ActivatedRoute){
    this.review = new Review();
    this.username = this.cookieService.get('token').split(':')[0];
    this.reviewInfo = new ReviewInfo();
    this.editOrCreate = 'create';
  }
  
  ngOnInit(): void {
    this.items=[
      {
        label:'Home',
        icon:'pi pi-home',
        routerLink:['/home']
      },
      {
        label:'Buscar juegos',
        icon:'pi pi-search',
        routerLink:['/games']
      },
      {
        label:'Ver mis reseñas',
        icon:'pi pi-book',
        routerLink:['/users/'+this.username+'/reviews']
      },
      {
        label:'Panel de administrador',
        icon:'pi pi-desktop',
        routerLink:['/admin'],
        visible:this.username=='admin'
      }
    ]
    this.itemsUser = [
      {
        label:'Cerrar sesión',
        icon:'pi pi-sign-out',
        command: () => {
          this.logout();
        }
      }
    ]

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
      this.reviewInfo.videojuego = this.review.videojuego;
      this.reviewInfo.username = this.review.username;
      this.reviewService.editReview(this.review.idReview ?? 0, this.reviewInfo).subscribe(
      //Comprobar si r.response tambien es EXISTS o ERROR y las validaciones y mostrar mensajes de error en consecuencia
        r =>{
          if(r.response == 'OK'){
            this.router.navigate(['/users/'+this.username+'/reviews']);
          }
        }
      );
    } else {
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

  logout() : void {
    this.cookieService.deleteAll('/');
    this.router.navigate(['/login']);
  }
}
