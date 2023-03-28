import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { RegisterResponse } from './register-response';
import { Usuario } from './usuario';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private url:string = 'http://localhost:8082/api/usuarios';
  user:Usuario;

  constructor(private http:HttpClient) { 
    this.user = new Usuario('', '');
  }

  register(username:string, password:string, repeatedPassword:string) : Observable<RegisterResponse> {
    if(username.length > 20){
      return of(new RegisterResponse('ERROR_LEN_USE'));
    }
    if(password.length > 25){
      return of(new RegisterResponse('ERROR_LEN_PASS'));
    }
    if(repeatedPassword.length > 25){
      return of(new RegisterResponse('ERROR_LEN_RPASS'));
    }

    if(password == repeatedPassword){
      this.user.username = username;
      this.user.password = password;
      return this.http.post<RegisterResponse>(this.url, this.user);
    } else{
      return of(new RegisterResponse('BAD'));
    }
  }
}
