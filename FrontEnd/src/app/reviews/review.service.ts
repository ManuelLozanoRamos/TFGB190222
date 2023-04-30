import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable, of } from 'rxjs';
import { Review } from './review';
import { ReviewResponse } from '../responses/review-response';
import { ReviewInfo } from './reviewInfo';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private url:string = 'http://localhost:8081/api/reviews';

  constructor(private http:HttpClient) { }

  //Obtener reviews
  getReviews(parametros:Map<string, string>):Observable<ReviewResponse>{
    let httpParams = new HttpParams();

    let notaIni = parametros.get("notaIni");
    let notaFin = parametros.get("notaFin");
    let fechaRegIni = parametros.get("fechaRegIni");
    let fechaRegFin = parametros.get("fechaRegFin");

    if(Number(notaIni) < 1 || Number(notaIni) > 10){
      return of(new ReviewResponse("ERROR_RAN_NINI", new Review(), []));
    }
    if(Number(notaFin) < 1 || Number(notaFin) > 10){
      return of(new ReviewResponse("ERROR_RAN_NFIN", new Review(), []));
    }
    if(Number(notaIni) > Number(notaFin)){
      return of(new ReviewResponse("ERROR_NINI_MAYOR_NFIN", new Review(), []));
    }
    if((notaIni != '' && notaFin == '') || (notaIni == '' && notaFin != '')){
      return of(new ReviewResponse("ERROR_SOLO_UNA_NOTA", new Review(), []));
    }
    if(fechaRegIni != '' && fechaRegFin != '' && String(fechaRegIni) > String(fechaRegFin)){
      return of(new ReviewResponse("ERROR_FINI_MAYOR_FFIN", new Review(), []));
    }
    if(fechaRegIni != '' && fechaRegFin == '' || (fechaRegIni == '' && fechaRegFin != '')){
      return of(new ReviewResponse("ERROR_SOLO_UNA_FECHA", new Review(), []));
    }

    parametros.forEach((value, key) =>{
      httpParams = httpParams.set(key, value)
    });

    return this.http.get<ReviewResponse>(this.url, {params:httpParams});
  }

  getReviewById(idReview:number):Observable<ReviewResponse>{
    return this.http.get<ReviewResponse>(this.url + '/' + encodeURIComponent(idReview));
  }

  //Crear review
  createReview(review:Review):Observable<ReviewResponse>{
    if(review.titulo.length > 75){
      return of(new ReviewResponse("ERROR_LEN_TIT", new Review, []));
    }
    if(review.comentario.length > 200){
      return of(new ReviewResponse("ERROR_LEN_COM", new Review(), []));
    }
    if(review.nota < 1 || review.nota > 10){
      return of(new ReviewResponse("ERROR_TAM_NOTA", new Review(), []));
    }

    return this.http.post<ReviewResponse>(this.url, review);
  }

  //Elimina una review
  delete(idReview:number):Observable<ReviewResponse>{
    return this.http.delete<ReviewResponse>(this.url + '/' + encodeURIComponent(idReview) + '/delete');
  }

  editReview(idReview:number, reviewInfo:ReviewInfo):Observable<ReviewResponse>{
    if(reviewInfo.titulo.length > 75){
      return of(new ReviewResponse("ERROR_LEN_TIT", new Review(), []));
    }
    if(reviewInfo.comentario.length > 200){
      return of(new ReviewResponse("ERROR_LEN_COM", new Review(), []));
    }
    if(reviewInfo.nota < 1 || reviewInfo.nota > 10){
      return of(new ReviewResponse("ERROR_TAM_NOTA", new Review(), []));
    }

    return this.http.put<ReviewResponse>(this.url + '/' + encodeURIComponent(idReview) + '/edit', reviewInfo);
  }
}
