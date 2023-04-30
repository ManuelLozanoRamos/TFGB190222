import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserResponse } from 'src/app/responses/user-response';


@Injectable({
  providedIn: 'root'
})
export class RequestChangePasswordService {

  private url:string = 'http://localhost:8082/api/usuarios';

  constructor(private http:HttpClient) { }

  requestChangePassword(mail:string) : Observable<UserResponse>{
    const regex = new RegExp('^[ \t\n]*$');
    if(regex.test(mail)){
      return of(new UserResponse("ERROR_EMPTY_USER", ''));
    }

    return this.http.post<UserResponse>(this.url + '/reset/password/send/mail', null, {params:{mail:mail}});
  }
}
