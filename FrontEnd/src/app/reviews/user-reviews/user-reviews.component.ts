import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Review } from '../review';
import { ReviewService } from '../review.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-user-reviews',
  templateUrl: './user-reviews.component.html',
  styleUrls: ['./user-reviews.component.css']
})
export class UserReviewsComponent implements OnInit{
  username:string;
  reviews:Review[];
  videojuego:string;
  notaIni:string;
  notaFin:string;
  fechaRegIni:string;
  fechaRegFin:string;
  order:string;
  orders:string[];

  items!:MenuItem[];
  itemsUser!:MenuItem[];

  constructor(private reviewService:ReviewService,
              private router:Router, private cookieService:CookieService){
    this.username = this.cookieService.get('token').split(':')[0];
    this.reviews = [];

    this.videojuego = '';
    this.notaIni = '1';
    this.notaFin = '10';
    this.fechaRegIni = '';
    this.fechaRegFin = '';
    this.order='';
    this.orders = ['Fecha registro descendente', 'Fecha registro ascendente', 'Nombre juego descendente', 'Nombre juego ascendente', 'Nota descendente', 'Nota ascendente'];

    this.searchAll();
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
  }

  search() : void {
    let parametros:Map<string, string> = new Map();
    let realizarPeticion:boolean = false;
    parametros.set('username', this.username);
    if(this.videojuego != ''){
      parametros.set('videojuego', this.videojuego);
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
    parametros.set('username', this.username);

    this.reviewService.getReviews(parametros).subscribe(
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
    this.cookieService.deleteAll('/');
    this.router.navigate(['/login']);
  }

}
