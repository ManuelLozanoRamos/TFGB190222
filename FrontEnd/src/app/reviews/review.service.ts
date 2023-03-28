import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable, of } from 'rxjs';
import { Review } from './review';
import { FormResponse } from './form-reviews/form-response';
import { SearchResponse } from './search-response';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private url:string = 'http://localhost:8081/api/reviews';

  constructor(private http:HttpClient) { }

  //Obtener reviews
  getReviews(game:string):Observable<SearchResponse>{
    if(String.length > 50){
      return of(new SearchResponse([], "ERROR_LEN_VID"));
    }

    return this.http.get<SearchResponse>(this.url,{params:{juego:game}});
  }

  //Crear review
  createReview(review:Review):Observable<FormResponse>{
    if(review.videojuego.length > 50){
      return of(new FormResponse("ERROR_LEN_VID"));
    }
    if(review.titulo.length > 50){
      return of(new FormResponse("ERROR_LEN_TIT"));
    }
    if(review.comentario.length > 150){
      return of(new FormResponse("ERROR_LEN_COM"));
    }
    if(review.nota < 1 || review.nota > 10){
      return of(new FormResponse("ERROR_TAM_NOTA"));
    }

    return this.http.post<FormResponse>(this.url, review);
  }
}
