import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { Game } from '../game';
import { GameService } from '../game.service';

@Component({
  selector: 'app-admin-games',
  templateUrl: './admin-games.component.html',
  styleUrls: ['./admin-games.component.css']
})
export class AdminGamesComponent {

  games:Game[];
  game:string;

  constructor(private gameService:GameService, private appComponent:AppComponent, private router:Router){
    this.games = [];
    this.game = '';
    this.searchAll();
  }

  searchByGame() : void {
    this.gameService.getGames(this.game).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.games = r.games 
    );
  }

  searchAll() : void {
    this.gameService.getGames("any").subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.games = r.games 
    );
  }


  delete(idGame:string) : void{
    this.gameService.delete(idGame).subscribe(
      r => window.location.reload()
    );
  }

}
