import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from 'src/app/app.component';
import { Game } from '../game';
import { GameService } from '../game.service';
import { GameInfo } from '../gameInfo';
import { MenuItem } from 'primeng/api';

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
              private router:Router, private activatedRoute:ActivatedRoute){
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
      //Comprobar si hay errore y demás
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
    const regex = new RegExp('^[ \t\n]*$');

    if(this.game.nombre == null || this.game.nombre.length == 0 || regex.test(this.game.nombre)){
      //Mensaje de que metas todo papi
    } else if(this.game.plataforma == null || this.game.plataforma.length == 0 || regex.test(this.game.plataforma)){
//Mensaje de que metas todo papi
    } else if(this.game.desarrolladora == null || this.game.desarrolladora.length == 0 || regex.test(this.game.desarrolladora)){
      //Mensaje de que metas todo papi
    } else if(this.game.genero1 == null || this.game.genero1.length == 0 || regex.test(this.game.genero1)){
      //Mensaje de que metas todo papi
    } else if(this.game.fechaLanzamiento == null || this.game.fechaLanzamiento.toString() == ''){
      //Mensaje de que metas todo papi
    }else {
      if(this.game.genero2 == null)  this.game.genero2 = '';
      if(this.game.genero3 == null)  this.game.genero3 = '';

      let genero1 = this.game.genero1;
      let genero2 = this.game.genero2;
      let genero3 = this.game.genero3;

      if(this.editOrCreate == 'edit'){
        if((genero1.length == 0 && genero2.length == 0 && genero3.length == 0)
           || (genero2.length != 0 && genero1.length == 0 ) 
           || (genero3.length != 0 && (genero1.length == 0 || genero2.length == 0))){
          //ERROR en la inserccion de los generos, meter bien"
          console.log("ERROR en la inserccion de los generos, meter bien")
        }
        else if(genero1.length > 25 || genero2.length > 25 || genero3.length > 25){
          //ERROR
          console.log("ERROR longitudes mal")
        }
        else {
          this.gameInfo.plataforma = this.game.plataforma;
          this.gameInfo.desarrolladora = this.game.desarrolladora;
          this.gameInfo.genero1 = genero1;
          this.gameInfo.genero2 = genero2;
          this.gameInfo.genero3 = genero3;
          this.gameInfo.fechaLanzamiento = this.game.fechaLanzamiento;
          this.gameService.editGame(this.game.nombre, this.gameInfo).subscribe(
          //Comprobar si r.response tambien es EXISTS o ERROR y las validaciones y mostrar mensajes de error en consecuencia
            r =>{
              if(r.response == 'OK'){
                this.router.navigate(['/admin/games']);
              }
            }
          );
        }
      } 
      else {
        if((genero1.length == 0 && genero2.length == 0 && genero3.length == 0)
           || (genero2.length != 0 && genero1.length == 0 ) 
           || (genero3.length != 0 && (genero1.length == 0 || genero2.length == 0))){
          //ERROR en la inserccion de los generos, meter bien"
          console.log("ERROR en la inserccion de los generos, meter bien")
        }
        else if(genero1.length > 25 || genero2.length > 25 || genero3.length > 25){
          //ERROR
          console.log("ERROR longitudes mal")
        }
        else {
          this.game.genero1 = genero1;
          this.game.genero2 = genero2;
          this.game.genero3 = genero3;
          this.game.fechaRegistro = new Date();
          this.gameService.createGame(this.game).subscribe(
          //Comprobar si r.response tambien es EXISTS o ERROR y las validaciones y mostrar mensajes de error en consecuencia
            r =>{
              if(r.response == 'OK'){
                this.router.navigate(['/admin']);
              }
            }
          );
        } 
      }
    }
  }

  logout() : void {
    this.cookieService.delete('token', '/');
    this.router.navigate(['/login']);
  }
}
