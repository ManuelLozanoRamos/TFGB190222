import { Component } from '@angular/core';
import { AppComponent } from '../app.component';
import { Game } from './game';
import { GameService } from './game.service';

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css']
})
export class GamesComponent {
  games:Game[];
  game:string;

  constructor(private gameService:GameService, private appComponent:AppComponent){
    this.games = [];
    this.game = '';
    this.searchAll();
  }
  
  ngOnInit(): void {
  }

  searchAll() : void {
    this.gameService.getGames("any").subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.games = r.games 
    );
  }

  search() : void {
    this.gameService.getGames(this.game).subscribe(
      //Comprobar si r.response es ERROR y validaciones y si es así entonces mostrar mensaje de error interno
      r => this.games = r.games 
    );
  }
}
