import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from 'src/app/app.component';
import { Game } from '../game';
import { GameService } from '../game.service';
import { GameInfo } from '../gameInfo';

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

  constructor(private gameService:GameService, private cookieService:CookieService,
              private router:Router, private activatedRoute:ActivatedRoute){
    this.username = this.cookieService.get('token');
    this.game = new Game();
    this.gameInfo = new GameInfo();
    this.editOrCreate = 'create';
  }
  ngOnInit(): void {
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

  logout() : void {
    this.cookieService.delete('token');
    this.router.navigate(['/login']);
  }
}
