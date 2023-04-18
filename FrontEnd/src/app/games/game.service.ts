import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
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

  //Obtener todos los games
  getAllGames():Observable<SearchResponse>{
    return this.http.get<SearchResponse>(this.url);
  }

  //Obtener games
  getGames(parametros:Map<string, string>):Observable<SearchResponse>{
    if(parametros.size != 0){
      let httpParams = new HttpParams();

      let notaMediaIni = parametros.get("notaMediaIni");
      let notaMediaFin = parametros.get("notaMediaFin");
      let fechaLanIni = parametros.get("fechaLanIni");
      let fechaLanFin = parametros.get("fechaLanFin");

      if(Number(notaMediaIni) < 1 || Number(notaMediaIni) > 10){
        return of(new SearchResponse([], "ERROR_RAN_NINI"));
      }
      if(Number(notaMediaFin) < 1 || Number(notaMediaFin) > 10){
        return of(new SearchResponse([], "ERROR_RAN_NFIN"));
      }
      if(Number(notaMediaIni) > Number(notaMediaFin)){
        return of(new SearchResponse([], "ERROR_NINI_MAYOR_NFIN"));
      }
      if((notaMediaIni != '' && notaMediaFin == '') || (notaMediaIni == '' && notaMediaFin != '')){
        return of(new SearchResponse([], "ERROR_SOLO_UNA_NOTA"));
      }
      if(fechaLanIni != '' && fechaLanFin != '' && String(fechaLanIni) > String(fechaLanFin)){
        return of(new SearchResponse([], "ERROR_FINI_MAYOR_FFIN"));
      }
      if(fechaLanIni != '' && fechaLanFin == '' || (fechaLanIni == '' && fechaLanFin != '')){
        return of(new SearchResponse([], "ERROR_SOLO_UNA_FECHA"));
      }

      parametros.forEach((value, key) =>{
        httpParams = httpParams.set(key, value)
      });

      return this.http.get<SearchResponse>(this.url, {params:httpParams});
    }
    else{
      return of(new SearchResponse([], "NO_PETICION"));
    }
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
