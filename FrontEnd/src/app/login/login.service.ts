import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { LoginResponse } from './login-response';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private url:string = 'http://localhost:8082/api/usuarios';

  constructor(private http:HttpClient) { }

  //comprobar si usuario está registrado
  isRegister(un:string, psw:string) : Observable<LoginResponse>{
    if(un.length > 20){
      return of(new LoginResponse("ERROR_LEN_USE"));
    }
    if(psw.length > 25){
      return of(new LoginResponse("ERROR_LEN_PASS"));
    }

    return this.http.get<LoginResponse>(this.url, {params:{username:un, password:psw}});
  }
}
