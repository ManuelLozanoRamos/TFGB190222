import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Review } from '../review';
import { ReviewService } from '../review.service';
import { ReviewInfo } from '../reviewInfo';
import { MenuItem, MessageService } from 'primeng/api';

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
              private router:Router, private activatedRoute:ActivatedRoute, private messageService:MessageService){
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
        r =>{
          if(r.response == 'OK'){
            this.messageService.clear();
            this.messageService.add({severity:'success', detail:'Reseña actualizada con éxito.'});
            setTimeout(() => {this.router.navigate(['/users/'+this.username+'/reviews']);}, 3000);
          } else if(r.response == 'EMPTY_NOTA'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce la nota de la reseña.'});
          } else if(r.response == 'EMPTY_TITULO'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce el título de la reseña.'});
          } else if(r.response == 'EMPTY_COMENTARIO'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce el comentario de la reseña.'});
          } else if(r.response == 'NOT_FOUND'){
            this.messageService.clear();
            this.messageService.add({severity:'error', detail:'No se ha encontrado la reseña a editar.'});
          } 
        },
        error => {
          this.messageService.clear();
          this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
        }
      );
    } else {
      this.review.username = this.username;
      this.reviewService.createReview(this.review).subscribe(
        r =>{
          if(r.response == 'OK'){
            this.messageService.clear();
            this.messageService.add({severity:'success', detail:'Reseña registrada con éxito.'});
            setTimeout(() => {this.router.navigate(['/games/'+this.review.videojuego+'/reviews']);}, 3000);
          } else if(r.response == 'EMPTY_NOTA'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce la nota de la reseña.'});
          } else if(r.response == 'EMPTY_TITULO'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce el título de la reseña.'});
          } else if(r.response == 'EMPTY_COMENTARIO'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce el comentario de la reseña.'});
          } else if(r.response == 'EXISTS'){
            this.messageService.clear();
            this.messageService.add({severity:'error', detail:'Ya has registrado una reseña para este juego'});
          } 
        },
        error => {
          this.messageService.clear();
          this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
        }
      );
    }
  }

  logout() : void {
    this.cookieService.deleteAll('/');
    this.router.navigate(['/login']);
  }
}
