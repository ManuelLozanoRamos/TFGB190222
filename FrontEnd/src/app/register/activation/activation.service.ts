import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserResponse } from '../../responses/user-response';


@Injectable({
  providedIn: 'root'
})
export class ActivationService {

  private url:string = 'http://localhost:8082/api/usuarios';

  constructor(private http:HttpClient) {
  }

  activate(username:string) : Observable<UserResponse> {
    return this.http.post<UserResponse>(this.url + '/' + username + '/activate', null);
  }
}
