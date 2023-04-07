import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable, of } from 'rxjs';
import { SearchResponse } from './search-response';
import { DeleteResponse } from './delete-response';
import { SearchByIdResponse } from './searchById-response';
import { EditResponse } from './edit-response';
import { Game } from './game';
import { GameInfo } from './gameInfo';
import { FormResponse } from './form-games/form-response';


@Injectable({
  providedIn: 'root'
})
export class GameService {

  private url:string = 'http://localhost:8083/api/games';

  constructor(private http:HttpClient) { }

  //Obtener games
  getGames(game:string):Observable<SearchResponse>{
    if(game.length > 75){
      return of(new SearchResponse([], "ERROR_LEN_VID"));
    }

    return this.http.get<SearchResponse>(this.url,{params:{juego:game}});
  }

  getGameById(idGame:number):Observable<SearchByIdResponse>{
    return this.http.get<SearchByIdResponse>(this.url + '/' + encodeURIComponent(idGame));
  }

  //Crear game
  createGame(game:Game):Observable<FormResponse>{
    if(game.nombre.length > 75){
      return of(new FormResponse("ERROR_LEN_NOM"));
    }
    if(game.plataforma.length > 40){
      return of(new FormResponse("ERROR_LEN_PLAT"));
    }
    if(game.desarrolladora.length > 50){
      return of(new FormResponse("ERROR_LEN_PLAT"));
    }

    return this.http.post<FormResponse>(this.url, game);
  }

  //Elimina un game
  delete(idGame:string):Observable<DeleteResponse>{
    return this.http.delete<DeleteResponse>(this.url + '/' + encodeURIComponent(idGame) + '/delete');
  }

  editGame(idGame:string, gameInfo:GameInfo):Observable<EditResponse>{
    if(gameInfo.plataforma.length > 40){
      return of(new FormResponse("ERROR_LEN_PLAT"));
    }
    if(gameInfo.desarrolladora.length > 50){
      return of(new FormResponse("ERROR_LEN_PLAT"));
    }

    return this.http.put<EditResponse>(this.url + '/' + encodeURIComponent(idGame) + '/edit', gameInfo);
  }
}
