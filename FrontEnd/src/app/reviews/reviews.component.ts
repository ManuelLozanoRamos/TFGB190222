import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Review } from './review';
import { ReviewService } from './review.service';
import { MenuItem, MessageService } from 'primeng/api';

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

  items!:MenuItem[];
  itemsUser!:MenuItem[];

  constructor(private reviewService:ReviewService, private activatedRoute:ActivatedRoute,
              private router:Router, private cookieService:CookieService, private messageService:MessageService){
    this.username = this.cookieService.get('token').split(':')[0];
    this.userReview = new Review;
    this.userHasReview = false;
    this.finished = false;
    this.reviews = [];
    this.game = '';

    this.user = '';
    this.notaIni = '1';
    this.notaFin = '10';
    this.fechaRegIni = '';
    this.fechaRegFin = '';
    this.order='';
    this.orders = ['Fecha registro descendente', 'Fecha registro ascendente', 'Nombre usuario descendente', 'Nombre usuario ascendente', 'Nota descendente', 'Nota ascendente'];
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
    if(this.notaIni != '' && this.notaIni != null){
      parametros.set('notaIni', this.notaIni);
      realizarPeticion = true;
    } 
    if(this.notaFin != '' && this.notaFin != null){
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
        r => {
          if(r.response == 'OK'){
            this.reviews = r.reviews;
          } else if(r.response == 'ERROR_SOLO_UNA_NOTA'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Debes introducir la nota inicial y la nota final, o ninguna de las dos.'});
          } else if(r.response == 'ERROR_NINI_MAYOR_NFIN'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'La nota inicial no puede ser mayor a la nota final.'});
          } else if(r.response == 'ERROR_SOLO_UNA_FECHA'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Debes introducir la fecha registro inicial y la fecha registro final, o ninguna de las dos.'});
          } else if(r.response == 'ERROR_FINI_MAYOR_FFIN'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'La fecha registro inicial no puede ser mayor a la nota registro final.'});
          }
        },
        error => {
          this.messageService.clear();
          this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
        }
      );
    } else {
      this.messageService.clear();
      this.messageService.add({severity:'warn', detail:'Introduce algún filtro u ordenación para realizar una búsqueda'});
    }
  }

  searchAll() : void {
    let parametros:Map<string, string> = new Map();
    parametros.set('videojuego', this.game);

    this.reviewService.getReviews(parametros).subscribe(
      r =>{this.reviews = r.reviews},
      error => {
        this.messageService.clear();
        this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
      }
    );
  }

  searchByUser() : void {
    let parametros:Map<string, string> = new Map();
    parametros.set('videojuego', this.game);
    parametros.set('username', this.username);

    this.reviewService.getReviews(parametros).subscribe(
      r =>{
        if(r.reviews.length != 0){
          this.userReview = r.reviews[0];
          this.userHasReview = true;
        } 
        this.finished = true;
      },
      error => {
        this.messageService.clear();
        this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
      }
    );
  }

  logout() : void {
    this.cookieService.deleteAll('/');
    this.router.navigate(['/login']);
  }
}
