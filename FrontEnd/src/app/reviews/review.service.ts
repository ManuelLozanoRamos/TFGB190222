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
    if(parametros.size != 0){
      let reviewInfo:ReviewInfo = new ReviewInfo();

      let notaIni = parametros.get("notaIni");
      let notaFin = parametros.get("notaFin");
      let fechaRegIni = parametros.get("fechaRegIni");
      let fechaRegFin = parametros.get("fechaRegFin");


      if((notaIni != null && notaFin == null) || (notaIni == null && notaFin != null)){
        return of(new ReviewResponse("ERROR_SOLO_UNA_NOTA", new Review(), []));
      }
      if(Number(notaIni) > Number(notaFin)){
        return of(new ReviewResponse("ERROR_NINI_MAYOR_NFIN", new Review(), []));
      }
      if((!fechaRegIni && fechaRegFin) || (fechaRegIni && !fechaRegFin)){
        return of(new ReviewResponse("ERROR_SOLO_UNA_FECHA", new Review(), []));
      }
      if(String(fechaRegIni) > String(fechaRegFin)){
        return of(new ReviewResponse("ERROR_FINI_MAYOR_FFIN", new Review(), []));
      }

      parametros.forEach((value, key) =>{
        reviewInfo[key] = value;
      });

      return this.http.post<ReviewResponse>(this.url + '/filter', reviewInfo);
    }
    else{
      return of(new ReviewResponse("NO_PETICION", new Review(), []));
    }
  }

  getReviewById(idReview:number):Observable<ReviewResponse>{
    return this.http.get<ReviewResponse>(this.url + '/' + encodeURIComponent(idReview));
  }

  //Crear review
  createReview(review:Review):Observable<ReviewResponse>{
    const regex = new RegExp('^[ \t\n]*$');

    if(review.nota == null){
      return of(new ReviewResponse("EMPTY_NOTA", new Review(), []));
    }
    if(review.titulo == null || review.titulo.length == 0 || regex.test(review.titulo)){
      return of(new ReviewResponse("EMPTY_TITULO", new Review(), []));
    }
    if(review.comentario == null || review.comentario.length == 0 || regex.test(review.comentario)){
      return of(new ReviewResponse("EMPTY_COMENTARIO", new Review(), []));
    }

    return this.http.post<ReviewResponse>(this.url, review);
  }

  //Elimina una review
  delete(idReview:number):Observable<ReviewResponse>{
    return this.http.delete<ReviewResponse>(this.url + '/' + encodeURIComponent(idReview) + '/delete');
  }

  editReview(idReview:number, reviewInfo:ReviewInfo):Observable<ReviewResponse>{
    const regex = new RegExp('^[ \t\n]*$');
    
    if(reviewInfo.nota == null){
      return of(new ReviewResponse("EMPTY_NOTA", new Review(), []));
    }
    if(reviewInfo.titulo == null || reviewInfo.titulo.length == 0 || regex.test(reviewInfo.titulo)){
      return of(new ReviewResponse("EMPTY_TITULO", new Review(), []));
    }
    if(reviewInfo.comentario == null || reviewInfo.comentario.length == 0 || regex.test(reviewInfo.comentario)){
      return of(new ReviewResponse("EMPTY_COMENTARIO", new Review(), []));
    }

    return this.http.put<ReviewResponse>(this.url + '/' + encodeURIComponent(idReview) + '/edit', reviewInfo);
  }
}
