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
      let gameInfo:GameInfo = new GameInfo();

      let notaMediaIni = parametros.get("notaMediaIni");
      let notaMediaFin = parametros.get("notaMediaFin");
      let fechaLanIni = parametros.get("fechaLanIni");
      let fechaLanFin = parametros.get("fechaLanFin");

      if((notaMediaIni != null && notaMediaFin == null) || (notaMediaIni == null && notaMediaFin != null)){
        return of(new GameResponse("ERROR_SOLO_UNA_NOTA", new Game(), []));
      }
      if(Number(notaMediaIni) > Number(notaMediaFin)){
        return of(new GameResponse("ERROR_NINI_MAYOR_NFIN", new Game(), []));
      }
      if((!fechaLanIni && fechaLanFin) || (fechaLanIni && !fechaLanFin)){
        return of(new GameResponse("ERROR_SOLO_UNA_FECHA", new Game(), []));
      }
      if(String(fechaLanIni) > String(fechaLanFin)){
        return of(new GameResponse("ERROR_FINI_MAYOR_FFIN", new Game(), []));
      }

      parametros.forEach((value, key) =>{
          gameInfo[key] = value;
      });

      return this.http.post<GameResponse>(this.url + '/filter', gameInfo);
    }
    else{
      return of(new GameResponse("NO_PETITION", new Game(), []));
    }
  }

  getGameById(idGame:number):Observable<GameResponse>{
    return this.http.get<GameResponse>(this.url + '/' + encodeURIComponent(idGame));
  }

  //Crear game
  createGame(game:Game):Observable<GameResponse>{
    const regex = new RegExp('^[ \t\n]*$');
    
    if(game.nombre == null || game.nombre.length == 0 || regex.test(game.nombre)){
      return of(new GameResponse("ERROR_NO_NOM", new Game(), []));
    } else if(game.desarrolladora == null || game.desarrolladora.length == 0 || regex.test(game.desarrolladora)){
      return of(new GameResponse("ERROR_NO_DESA", new Game(), []));
    } else if(game.plataforma1 == null || game.plataforma1.length == 0 || regex.test(game.plataforma1)){
      return of(new GameResponse("ERROR_NO_PLAT", new Game(), []));
    }  else if(game.genero1 == null || game.genero1.length == 0 || regex.test(game.genero1)){
      return of(new GameResponse("ERROR_NO_GEN", new Game(), []));
    } else if(game.fechaLanzamiento == null || game.fechaLanzamiento.toString() == ''){
      return of(new GameResponse("ERROR_NO_FECH", new Game(), []));
    }

    if((game.plataforma3 != null && game.plataforma3.length != 0) && (game.plataforma2 == null || game.plataforma2.length == 0)){
      return of(new GameResponse("ERROR_BAD_PLAT", new Game(), []));
    }
    if((game.genero3 != null && game.genero3.length != 0) && (game.genero2 == null || game.genero2.length == 0)){
        return of(new GameResponse("ERROR_BAD_GEN", new Game(), []));
    }

    return this.http.post<GameResponse>(this.url, game);
  }

  //Elimina un game
  delete(idGame:string):Observable<GameResponse>{
    return this.http.delete<GameResponse>(this.url + '/' + encodeURIComponent(idGame) + '/delete');
  }

  //Edita un game
  editGame(idGame:string, gameInfo:GameInfo):Observable<GameResponse>{
    const regex = new RegExp('^[ \t\n]*$');
    if(gameInfo.desarrolladora == null || gameInfo.desarrolladora.length == 0 || regex.test(gameInfo.desarrolladora)){
      return of(new GameResponse("ERROR_NO_DESA", new Game(), []));
    } else if(gameInfo.plataforma1 == null || gameInfo.plataforma1.length == 0 || regex.test(gameInfo.plataforma1)){
      return of(new GameResponse("ERROR_NO_PLAT", new Game(), []));
    }  else if(gameInfo.genero1 == null || gameInfo.genero1.length == 0 || regex.test(gameInfo.genero1)){
      return of(new GameResponse("ERROR_NO_GEN", new Game(), []));
    } else if(gameInfo.fechaLanzamiento == null || gameInfo.fechaLanzamiento.toString() == ''){
      return of(new GameResponse("ERROR_NO_FECH", new Game(), []));
    }

    if((gameInfo.plataforma3 != null && gameInfo.plataforma3.length != 0 && !regex.test(gameInfo.plataforma3)) && (gameInfo.plataforma2 == null || gameInfo.plataforma2.length == 0 || regex.test(gameInfo.plataforma2))){
      return of(new GameResponse("ERROR_BAD_PLAT", new Game(), []));
    }
    if((gameInfo.genero3 != null && gameInfo.genero3.length != 0 && !regex.test(gameInfo.genero3)) && (gameInfo.genero2 == null || gameInfo.genero2.length == 0 || regex.test(gameInfo.genero2))){
        return of(new GameResponse("ERROR_BAD_GEN", new Game(), []));
    }

    return this.http.put<GameResponse>(this.url + '/' + encodeURIComponent(idGame) + '/edit', gameInfo);
  }
}
