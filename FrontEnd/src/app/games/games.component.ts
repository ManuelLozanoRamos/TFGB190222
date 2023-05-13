import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Game } from './game';
import { GameService } from './game.service';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css']
})
export class GamesComponent implements OnInit{
  username:string;
  games:Game[];
  nombre:string;
  plataforma1:string;
  plataforma2:string;
  plataforma3:string;
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

  constructor(private gameService:GameService, private router:Router, private cookieService:CookieService,
              private messageService:MessageService){
    this.username = this.cookieService.get('token').split(':')[0];
    this.games = [];
    this.nombre = '';
    this.plataforma1 = '';
    this.plataforma2 = '';
    this.plataforma3 = '';
    this.desarrolladora = '';
    this.genero1 = '';
    this.genero2 = '';
    this.genero3 = '';
    this.notaMediaIni = '1';
    this.notaMediaFin = '10';
    this.fechaLanIni = '';
    this.fechaLanFin = '';
    this.orders = ['Fecha lanzamiento descendente', 'Fecha lanzamiento ascendente', 'Nombre descendente', 'Nombre ascendente', 'Nota media descendente', 'Nota media ascendente'];
    this.order = '';

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
  
  
  searchAll() : void {
    this.gameService.getAllGames().subscribe(
      r =>{
        this.games = r.games;
        this.games.forEach((value) => {
          value.generos = value.genero1;
          if(value.genero2 != null && value.genero2 != '') value.generos += ', ' + value.genero2;
          if(value.genero3 != null && value.genero3 != '') value.generos += ', ' + value.genero3;
          value.plataformas = value.plataforma1;
          if(value.plataforma2 != null && value.plataforma2 != '') value.plataformas += ', ' + value.plataforma2;
          if(value.plataforma3 != null && value.plataforma3 != '') value.plataformas += ', ' + value.plataforma3;
        });
      },
      error => {
        this.messageService.clear();
        this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
      }
    );
  }

  search() : void {
    let parametros:Map<string, string> = new Map();
    if(this.nombre != '') parametros.set('nombre', this.nombre);
    if(this.plataforma1 != '') parametros.set('plataforma1', this.plataforma1);
    if(this.plataforma2 != '') parametros.set('plataforma2', this.plataforma2);
    if(this.plataforma3 != '') parametros.set('plataforma3', this.plataforma3);
    if(this.desarrolladora != '') parametros.set('desarrolladora', this.desarrolladora);
    if(this.genero1 != '') parametros.set('genero1', this.genero1);
    if(this.genero2 != '') parametros.set('genero2', this.genero2);
    if(this.genero3 != '') parametros.set('genero3', this.genero3);
    if(this.notaMediaIni != '' && this.notaMediaIni != null) parametros.set("notaMediaIni", this.notaMediaIni);
    if(this.notaMediaFin != '' && this.notaMediaFin != null) parametros.set("notaMediaFin", this.notaMediaFin);
    if(this.fechaLanIni != '') parametros.set("fechaLanIni", this.fechaLanIni);
    if(this.fechaLanFin != '') parametros.set("fechaLanFin", this.fechaLanFin);
    if(this.order != '') parametros.set("order", this.order);

    this.gameService.getGames(parametros).subscribe(
      r =>{
        if(r.response == 'OK'){
          this.games = r.games;
          this.games.forEach((value) => {
            value.generos = value.genero1;
            if(value.genero2 != null && value.genero2 != '') value.generos += ', ' + value.genero2;
            if(value.genero3 != null && value.genero3 != '') value.generos += ', ' + value.genero3;
            value.plataformas = value.plataforma1;
            if(value.plataforma2 != null && value.plataforma2 != '') value.plataformas += ', ' + value.plataforma2;
            if(value.plataforma3 != null && value.plataforma3 != '') value.plataformas += ', ' + value.plataforma3;
          });
        } else if(r.response == 'ERROR_SOLO_UNA_NOTA'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Debes introducir la nota media inicial y la nota media final, o ninguna de las dos.'});
        } else if(r.response == 'ERROR_NINI_MAYOR_NFIN'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'La nota media inicial no puede ser mayor a la nota media final'});
        } else if(r.response == 'ERROR_SOLO_UNA_FECHA'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Debes introducir la fecha lanzamiento inicial y la fecha lanzamiento final, o ninguna de las dos.'});
        } else if(r.response == 'ERROR_FINI_MAYOR_FFIN'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'La fecha lanzamiento inicial no puede ser mayor a la nota lanzamiento final'});
        } else if(r.response == 'NO_PETITION'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce algún filtro u ordenación para realizar una búsqueda'});
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
