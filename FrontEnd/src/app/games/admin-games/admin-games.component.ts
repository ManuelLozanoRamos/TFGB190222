import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Game } from '../game';
import { GameService } from '../game.service';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-admin-games',
  templateUrl: './admin-games.component.html',
  styleUrls: ['./admin-games.component.css']
})
export class AdminGamesComponent implements OnInit{
  username:string;
  games:Game[];
  nombre:string;
  plataforma:string;
  desarrolladora:string;
  genero1:string;
  genero2:string;
  genero3:string;
  notaMediaIni:string;
  notaMediaFin:string;
  fechaLanIni:string;
  fechaLanFin:string;
  order:string;
  orders:string[];

  items!:MenuItem[];
  itemsUser!:MenuItem[];

  constructor(private gameService:GameService, private router:Router, private cookieService:CookieService){
    this.username = this.cookieService.get('token').split(':')[0];
    this.games = [];
    this.nombre = '';
    this.plataforma = '';
    this.desarrolladora = '';
    this.genero1 = '';
    this.genero2 = '';
    this.genero3 = '';
    this.notaMediaIni = '';
    this.notaMediaFin = '';
    this.fechaLanIni = '';
    this.fechaLanFin = '';
    this.order='';
    this.orders = ['Fecha Descendiente', 'Fecha Ascendiente', 'Nombre Descendente', 'Nombre Ascendente', 'Nota Descendente', 'Nota Asccendente'];

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
    if(this.nombre != '') parametros.set('nombre', this.nombre);
    if(this.plataforma != '') parametros.set('plataforma', this.plataforma);
    if(this.desarrolladora != '') parametros.set('desarrolladora', this.desarrolladora);
    if(this.genero1 != '') parametros.set('genero1', this.genero1);
    if(this.genero2 != '') parametros.set('genero2', this.genero2);
    if(this.genero3 != '') parametros.set('genero3', this.genero3);
    if(this.notaMediaIni != '') parametros.set("notaMediaIni", this.notaMediaIni);
    if(this.notaMediaFin != '') parametros.set("notaMediaFin", this.notaMediaFin);
    if(this.fechaLanIni != '') parametros.set("fechaLanIni", this.fechaLanIni);
    if(this.fechaLanFin != '') parametros.set("fechaLanFin", this.fechaLanFin);
    if(this.order != '') parametros.set("order", this.order);

    this.gameService.getGames(parametros).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r =>{
        this.games = r.games;
        this.games.forEach((value) => {
          value.generos = value.genero1;
          if(value.genero2 != null && value.genero2 != '') value.generos += ', ' + value.genero2;
          if(value.genero3 != null && value.genero3 != '') value.generos += ', ' + value.genero3;
        });
      }
    );
  }

  searchAll() : void {
    this.gameService.getAllGames().subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r =>{
        this.games = r.games;
        this.games.forEach((value) => {
          value.generos = value.genero1;
          if(value.genero2 != null && value.genero2 != '') value.generos += ', ' + value.genero2;
          if(value.genero3 != null && value.genero3 != '') value.generos += ', ' + value.genero3;
        });
      }
    );
  }


  delete(idGame:string) : void{
    this.gameService.delete(idGame).subscribe(
      r => window.location.reload()
    );
  }

  logout() : void {
    this.cookieService.delete('token', '/');
    this.router.navigate(['/login']);
  }

}
