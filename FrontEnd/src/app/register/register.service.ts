import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserResponse } from '../responses/user-response';
import { Usuario } from './usuario';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private url:string = 'http://localhost:8082/api/usuarios';
  user:Usuario;

  constructor(private http:HttpClient) { 
    this.user = new Usuario('', '', '');
  }

  register(username:string, password:string, repeatedPassword:string, mail:string) : Observable<UserResponse> {
    const regex = new RegExp('^[ \t\n]*$');
    if(regex.test(username)){
      return of(new UserResponse('ERROR_EMPTY_USER', ''));
    }
    if(regex.test(mail)){
      return of(new UserResponse('ERROR_EMPTY_MAIL', ''));
    }
    if(regex.test(password)){
      return of(new UserResponse('ERROR_EMPTY_PASS', ''));
    }
    if(regex.test(repeatedPassword)){
      return of(new UserResponse('ERROR_EMPTY_RPASS', ''));
    }
    if(password != repeatedPassword){
      return of(new UserResponse('ERROR_NOT_EQ_PASS', ''));
    }

    this.user.username = username;
    this.user.password = password;
    this.user.mail = mail;
    return this.http.post<UserResponse>(this.url, this.user);
  }
}
