import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from 'src/app/app.component';
import { Game } from '../game';
import { GameService } from '../game.service';
import { GameInfo } from '../gameInfo';
import { MenuItem, MessageService } from 'primeng/api';

@Component({
  selector: 'app-form-games',
  templateUrl: './form-games.component.html',
  styleUrls: ['./form-games.component.css']
})
export class FormGamesComponent implements OnInit{
  username:string;
  game:Game;
  gameInfo:GameInfo;
  editOrCreate:string;

  items!:MenuItem[];
  itemsUser!:MenuItem[];

  constructor(private gameService:GameService, private cookieService:CookieService,
              private router:Router, private activatedRoute:ActivatedRoute, private messageService:MessageService){
    this.username = this.cookieService.get('token').split(':')[0];
    this.game = new Game();
    this.gameInfo = new GameInfo();
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
      r => {
        let id=r['id'];
        if(id){
          this.gameService.getGameById(id).subscribe(
            resp =>{
              this.game = resp.game;
              this.editOrCreate = 'edit';
            } 
          );
        }
      }
    );
  }

  createGame(){
    if(this.editOrCreate == 'edit'){
      this.gameInfo.nombre = this.game.nombre;
      this.gameInfo.plataforma1 = this.game.plataforma1;
      this.gameInfo.plataforma2 = this.game.plataforma2;
      this.gameInfo.plataforma3 = this.game.plataforma3;
      this.gameInfo.desarrolladora = this.game.desarrolladora;
      this.gameInfo.genero1 = this.game.genero1;
      this.gameInfo.genero2 = this.game.genero2;
      this.gameInfo.genero3 = this.game.genero3;
      this.gameInfo.fechaLanzamiento = this.game.fechaLanzamiento;
      if(this.gameInfo.genero2 == '')  this.gameInfo.genero2 = null;
      if(this.gameInfo.genero3 == '')  this.gameInfo.genero3 = null;
      if(this.gameInfo.plataforma2 == '')  this.gameInfo.plataforma2 = null;
      if(this.gameInfo.plataforma3 == '')  this.gameInfo.plataforma3 = null;
      
      this.gameService.editGame(this.game.nombre ?? '', this.gameInfo).subscribe(
        r =>{
          if(r.response == 'OK'){
            this.messageService.clear();
            this.messageService.add({severity:'success', detail:'Juego actualizado con éxito.'});
            setTimeout(() => {this.router.navigate(['/admin/games'])}, 3000);
          } else if(r.response == 'ERROR_NO_DESA'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce la desarrolladora del juego.'});
          } else if(r.response == 'ERROR_NO_PLAT'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce al menos la plataforma 1 de lanzamiento del juego.'});
          } else if(r.response == 'ERROR_NO_GEN'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce al menos el género 1 al que pertenece el juegp.'});
          } else if(r.response == 'ERROR_NO_FECH'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce la fecha de lanzamiento del juego.'});
          } else if(r.response == 'ERROR_BAD_PLAT'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce plataformas de manera continua.'});
          } else if(r.response == 'ERROR_BAD_GEN'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce géneros de manera continua.'});
          } else if(r.response == 'NOT_FOUND'){
            this.messageService.clear();
            this.messageService.add({severity:'error', detail:'El juego no ha sido encontrado.'});
          }
        },
        error => {
          this.messageService.clear();
          this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
        }
      );
    } 
    else {
      this.game.fechaRegistro = new Date();
      this.gameService.createGame(this.game).subscribe(
        r =>{
          if(r.response == 'OK'){
            this.messageService.clear();
            this.messageService.add({severity:'success', detail:'Juego registrado con éxito.'});
            setTimeout(() => {this.router.navigate(['/admin'])}, 3000);
          } else if(r.response == 'ERROR_NO_NOM'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce el nombre del juego.'});
          } else if(r.response == 'ERROR_NO_DESA'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce la desarrolladora del juego.'});
          } else if(r.response == 'ERROR_NO_PLAT'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce al menos la plataforma 1 de lanzamiento del juego.'});
          } else if(r.response == 'ERROR_NO_GEN'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce al menos el género 1 al que pertenece el juegp.'});
          } else if(r.response == 'ERROR_NO_FECH'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce la fecha de lanzamiento del juego.'});
          } else if(r.response == 'ERROR_BAD_PLAT'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce plataformas de manera continua.'});
          } else if(r.response == 'ERROR_BAD_GEN'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce géneros de manera continua.'});
          } else if(r.response == 'EXISTS'){
            this.messageService.clear();
            this.messageService.add({severity:'error', detail:'El juego ya se encuentra registrado en el sistema.'});
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
