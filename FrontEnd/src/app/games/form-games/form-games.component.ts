import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  game:Game;
  genero1:string;
  genero2:string;
  genero3:string;  
  gameInfo:GameInfo;
  editOrCreate:string;

  constructor(private appComponent:AppComponent, private gameService:GameService, 
              private router:Router, private activatedRoute:ActivatedRoute){
    this.game = new Game();
    this.genero1 = '';
    this.genero2 = '';
    this.genero3 = '';
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
              let generos:string[] = this.game.generos.split(", ");
              this.genero1 = generos[0];
              if(generos[1]) this.genero2 = generos[1];
              if(generos[2]) this.genero3 = generos[2];
              this.editOrCreate = 'edit';
            } 
          );
        }
      }
    );
  }

  createGame(){
    if(this.editOrCreate == 'edit'){
      if((this.genero1.length == 0 && this.genero2.length == 0 && this.genero3.length == 0)
         || (this.genero2.length != 0 && this.genero1.length == 0 ) 
         || (this.genero3.length != 0 && (this.genero1.length == 0 || this.genero2.length == 0))){
        //ERROR en la inserccion de los generos, meter bien"
        console.log("ERROR en la inserccion de los generos, meter bien")
      }
      else if(this.genero1.length > 25 || this.genero2.length > 25 || this.genero3.length > 25){
        //ERROR
        console.log("ERROR longitudes mal")
      }
      else {
        this.gameInfo.plataforma = this.game.plataforma;
        this.gameInfo.desarrolladora = this.game.desarrolladora;
        this.gameInfo.generos = this.genero1;
        if(this.genero2.length != 0){
          this.gameInfo.generos += ", "+this.genero2;
        }
        if(this.genero3.length != 0){
          this.gameInfo.generos += ", "+this.genero3;
        }
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
      if((this.genero1.length == 0 && this.genero2.length == 0 && this.genero3.length == 0)
         || (this.genero2.length != 0 && this.genero1.length == 0 ) 
         || (this.genero3.length != 0 && (this.genero1.length == 0 || this.genero2.length == 0))){
        //ERROR en la inserccion de los generos, meter bien"
        console.log("ERROR en la inserccion de los generos, meter bien")
      }
      else if(this.genero1.length > 25 || this.genero2.length > 25 || this.genero3.length > 25){
        //ERROR
        console.log("ERROR longitudes mal")
      }
      else {
        this.game.generos = this.genero1;
        if(this.genero2.length != 0){
          this.game.generos += ", "+this.genero2;
        }
        if(this.genero3.length != 0){
          this.game.generos += ", "+this.genero3;
        }
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
