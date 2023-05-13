import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Review } from '../review';
import { ReviewService } from '../review.service';
import { MenuItem, MessageService } from 'primeng/api';

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

  constructor(private reviewService:ReviewService, private messageService:MessageService,
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
    parametros.set('username', this.username);

    this.reviewService.getReviews(parametros).subscribe(
      r =>{this.reviews = r.reviews },
      error => {
        this.messageService.clear();
        this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
      }
    );
  }


  delete(idReview:number) : void{
    this.reviewService.delete(idReview).subscribe(
      r => {
        if(r.response == 'OK'){
          this.messageService.clear();
          this.messageService.add({severity:'success', detail:'Reseña eliminada con éxito.'});
          setTimeout(() => {window.location.reload();}, 3000);
        } else if(r.response == 'NOT_FOUND'){
          this.messageService.clear();
          this.messageService.add({severity:'error', detail:'No se ha podido encontrar la reseña'});
        }
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
