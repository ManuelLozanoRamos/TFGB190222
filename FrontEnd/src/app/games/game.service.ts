import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable, of } from 'rxjs';
import { Game } from './game';
import { GameInfo } from './gameInfo';
import { GameResponse } from '../responses/game-response';


@Injectable({
  providedIn: 'root'
})
export class GameService {

  private url:string = 'http://localhost:8083/api/games';

  constructor(private http:HttpClient) { }

  //Obtener todos los games
  getAllGames():Observable<GameResponse>{
    return this.http.get<GameResponse>(this.url);
  }

  //Obtener games
  getGames(parametros:Map<string, string>):Observable<GameResponse>{
    if(parametros.size != 0){
      let httpParams = new HttpParams();

      let notaMediaIni = parametros.get("notaMediaIni");
      let notaMediaFin = parametros.get("notaMediaFin");
      let fechaLanIni = parametros.get("fechaLanIni");
      let fechaLanFin = parametros.get("fechaLanFin");

      if(Number(notaMediaIni) < 1 || Number(notaMediaIni) > 10){
        return of(new GameResponse("ERROR_RAN_NINI",new Game() , []));
      }
      if(Number(notaMediaFin) < 1 || Number(notaMediaFin) > 10){
        return of(new GameResponse("ERROR_RAN_NFIN", new Game(), []));
      }
      if(Number(notaMediaIni) > Number(notaMediaFin)){
        return of(new GameResponse("ERROR_NINI_MAYOR_NFIN", new Game(), []));
      }
      if((notaMediaIni != '' && notaMediaFin == '') || (notaMediaIni == '' && notaMediaFin != '')){
        return of(new GameResponse("ERROR_SOLO_UNA_NOTA", new Game(), []));
      }
      if(fechaLanIni != '' && fechaLanFin != '' && String(fechaLanIni) > String(fechaLanFin)){
        return of(new GameResponse("ERROR_FINI_MAYOR_FFIN", new Game(), []));
      }
      if(fechaLanIni != '' && fechaLanFin == '' || (fechaLanIni == '' && fechaLanFin != '')){
        return of(new GameResponse("ERROR_SOLO_UNA_FECHA", new Game(), []));
      }

      parametros.forEach((value, key) =>{
        httpParams = httpParams.set(key, value)
      });

      return this.http.get<GameResponse>(this.url, {params:httpParams});
    }
    else{
      return of(new GameResponse("NO_PETICION", new Game(), []));
    }
  }

  getGameById(idGame:number):Observable<GameResponse>{
    return this.http.get<GameResponse>(this.url + '/' + encodeURIComponent(idGame));
  }

  //Crear game
  createGame(game:Game):Observable<GameResponse>{
    if(game.nombre.length > 75){
      return of(new GameResponse("ERROR_LEN_NOM", new Game(), []));
    }
    if(game.plataforma.length > 40){
      return of(new GameResponse("ERROR_LEN_PLAT", new Game(), []));
    }
    if(game.desarrolladora.length > 50){
      return of(new GameResponse("ERROR_LEN_PLAT", new Game(), []));
    }

    return this.http.post<GameResponse>(this.url, game);
  }

  //Elimina un game
  delete(idGame:string):Observable<GameResponse>{
    return this.http.delete<GameResponse>(this.url + '/' + encodeURIComponent(idGame) + '/delete');
  }

  editGame(idGame:string, gameInfo:GameInfo):Observable<GameResponse>{
    if(gameInfo.plataforma.length > 40){
      return of(new GameResponse("ERROR_LEN_PLAT", new Game(), []));
    }
    if(gameInfo.desarrolladora.length > 50){
      return of(new GameResponse("ERROR_LEN_PLAT", new Game(), []));
    }

    return this.http.put<GameResponse>(this.url + '/' + encodeURIComponent(idGame) + '/edit', gameInfo);
  }
}
