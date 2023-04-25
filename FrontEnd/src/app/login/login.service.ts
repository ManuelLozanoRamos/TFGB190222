import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserResponse } from '../register/user-response';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private url:string = 'http://localhost:8082/api/usuarios';

  constructor(private http:HttpClient) { }

  //comprobar si usuario está registrado
  isRegistered(username:string, password:string) : Observable<UserResponse>{
    const regex = new RegExp('^[ \t\n]*$');
    if(regex.test(username)){
      return of(new UserResponse("ERROR_EMPTY_USER"));
    }
    if(regex.test(password)){
      return of(new UserResponse("ERROR_EMPTY_PASS"));
    }

    return this.http.get<UserResponse>(this.url, {params:{username:username, password:password}});
  }
}
