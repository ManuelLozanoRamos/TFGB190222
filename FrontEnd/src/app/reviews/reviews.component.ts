import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from '../app.component';
import { Review } from './review';
import { ReviewService } from './review.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit{
  username:string;
  userReview:Review;
  userHasReview:boolean;
  finished:boolean;
  reviews:Review[];
  game:string;

  user:string;
  notaIni:string;
  notaFin:string;
  fechaRegIni:string;
  fechaRegFin:string;
  order:string;
  orders:string[];

  constructor(private reviewService:ReviewService, private activatedRoute:ActivatedRoute,
              private router:Router, private cookieService:CookieService){
    this.username = this.cookieService.get('token');
    this.userReview = new Review;
    this.userHasReview = false;
    this.finished = false;
    this.reviews = [];
    this.game = '';

    this.user = '';
    this.notaIni = '';
    this.notaFin = '';
    this.fechaRegIni = '';
    this.fechaRegFin = '';
    this.order='';
    this.orders = ['Fecha Descendiente', 'Fecha Ascendiente', 'Juego Descendente', 'Juego Ascendente', 'Nota Descendente', 'Nota Asccendente'];
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
    this.searchByUser();
    this.searchAll();
  }

  search() : void {
    let parametros:Map<string, string> = new Map();
    let realizarPeticion:boolean = false;
    parametros.set('videojuego', this.game);
    if(this.user != ''){
      parametros.set('username', this.user);
      realizarPeticion = true;
    } 
    if(this.notaIni != ''){
      parametros.set('notaIni', this.notaIni);
      realizarPeticion = true;
    } 
    if(this.notaFin != ''){
      parametros.set('notaFin', this.notaFin);
      realizarPeticion = true;
    } 
    if(this.fechaRegIni != ''){
      parametros.set('fechaRegIni', this.fechaRegIni);
      realizarPeticion = true;
    } 
    if(this.fechaRegFin != ''){
      parametros.set('fechaRegFin', this.fechaRegFin);
      realizarPeticion = true;
    } 
    if(this.order != ''){
      parametros.set("order", this.order);
      realizarPeticion = true;
    } 

    if(realizarPeticion){
      this.reviewService.getReviews(parametros).subscribe(
        //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
        r => this.reviews = r.reviews 
      );
    }
  }

  searchAll() : void {
    let parametros:Map<string, string> = new Map();
    parametros.set('videojuego', this.game);

    this.reviewService.getReviews(parametros).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.reviews = r.reviews 
    );
  }

  searchByUser() : void {
    let parametros:Map<string, string> = new Map();
    parametros.set('videojuego', this.game);
    parametros.set('username', this.username);

    this.reviewService.getReviews(parametros).subscribe(
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

  logout() : void {
    this.cookieService.delete('token');
    this.router.navigate(['/login']);
  }
}
