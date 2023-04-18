import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable, of } from 'rxjs';
import { Review } from './review';
import { FormResponse } from './form-reviews/form-response';
import { SearchResponse } from './search-response';
import { DeleteResponse } from './delete-response';
import { SearchByIdResponse } from './searchById-response';
import { EditResponse } from './edit-response';
import { ReviewInfo } from './reviewInfo';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private url:string = 'http://localhost:8081/api/reviews';

  constructor(private http:HttpClient) { }

  //Obtener reviews
  getReviews(parametros:Map<string, string>):Observable<SearchResponse>{
    let httpParams = new HttpParams();

    let notaIni = parametros.get("notaIni");
    let notaFin = parametros.get("notaFin");
    let fechaRegIni = parametros.get("fechaRegIni");
    let fechaRegFin = parametros.get("fechaRegFin");

    if(Number(notaIni) < 1 || Number(notaIni) > 10){
      return of(new SearchResponse([], "ERROR_RAN_NINI"));
    }
    if(Number(notaFin) < 1 || Number(notaFin) > 10){
      return of(new SearchResponse([], "ERROR_RAN_NFIN"));
    }
    if(Number(notaIni) > Number(notaFin)){
      return of(new SearchResponse([], "ERROR_NINI_MAYOR_NFIN"));
    }
    if((notaIni != '' && notaFin == '') || (notaIni == '' && notaFin != '')){
      return of(new SearchResponse([], "ERROR_SOLO_UNA_NOTA"));
    }
    if(fechaRegIni != '' && fechaRegFin != '' && String(fechaRegIni) > String(fechaRegFin)){
      return of(new SearchResponse([], "ERROR_FINI_MAYOR_FFIN"));
    }
    if(fechaRegIni != '' && fechaRegFin == '' || (fechaRegIni == '' && fechaRegFin != '')){
      return of(new SearchResponse([], "ERROR_SOLO_UNA_FECHA"));
    }

    parametros.forEach((value, key) =>{
      httpParams = httpParams.set(key, value)
    });

    return this.http.get<SearchResponse>(this.url, {params:httpParams});
  }

  getReviewById(idReview:number):Observable<SearchByIdResponse>{
    return this.http.get<SearchByIdResponse>(this.url + '/' + encodeURIComponent(idReview));
  }

  //Crear review
  createReview(review:Review):Observable<FormResponse>{
    if(review.titulo.length > 75){
      return of(new FormResponse("ERROR_LEN_TIT"));
    }
    if(review.comentario.length > 200){
      return of(new FormResponse("ERROR_LEN_COM"));
    }
    if(review.nota < 1 || review.nota > 10){
      return of(new FormResponse("ERROR_TAM_NOTA"));
    }

    return this.http.post<FormResponse>(this.url, review);
  }

  //Elimina una review
  delete(idReview:number):Observable<DeleteResponse>{
    return this.http.delete<DeleteResponse>(this.url + '/' + encodeURIComponent(idReview) + '/delete');
  }

  editReview(idReview:number, reviewInfo:ReviewInfo):Observable<EditResponse>{
    if(reviewInfo.titulo.length > 75){
      return of(new FormResponse("ERROR_LEN_TIT"));
    }
    if(reviewInfo.comentario.length > 200){
      return of(new FormResponse("ERROR_LEN_COM"));
    }
    if(reviewInfo.nota < 1 || reviewInfo.nota > 10){
      return of(new FormResponse("ERROR_TAM_NOTA"));
    }

    return this.http.put<EditResponse>(this.url + '/' + encodeURIComponent(idReview) + '/edit', reviewInfo);
  }
}
