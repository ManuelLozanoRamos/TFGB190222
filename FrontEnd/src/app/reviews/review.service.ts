import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable, of } from 'rxjs';
import { Review } from './review';
import { FormResponse } from './form-reviews/form-response';
import { SearchResponse } from './search-response';
import { DeleteResponse } from './delete-response';
import { SearchByIdResponse } from './searchById-response';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private url:string = 'http://localhost:8081/api/reviews';

  constructor(private http:HttpClient) { }

  //Obtener reviews
  getReviews(game:string, username:string):Observable<SearchResponse>{
    if(String.length > 50){
      return of(new SearchResponse([], "ERROR_LEN_VID"));
    }

    if(String.length > 20){
      username = "any";
    }

    return this.http.get<SearchResponse>(this.url,{params:{juego:game, username:username}});
  }

  getReviewById(idReview:number):Observable<SearchByIdResponse>{
    return this.http.get<SearchByIdResponse>(this.url + '/' + encodeURIComponent(idReview));
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

  //Elimina una review
  delete(idReview:number):Observable<DeleteResponse>{
    return this.http.delete<DeleteResponse>(this.url + '/' + encodeURIComponent(idReview) + '/delete');
  }
}
