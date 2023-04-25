import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserResponse } from 'src/app/register/user-response';

@Injectable({
  providedIn: 'root'
})
export class ChangePasswordService {

  private url:string = 'http://localhost:8082/api/usuarios';

  constructor(private http:HttpClient) { }

  changePassword(user:string, newPassword:string, repNewPassword:string) : Observable<UserResponse>{
    const regex = new RegExp('^[ \t\n]*$');
    if(regex.test(newPassword)){
      return of(new UserResponse("ERROR_EMPTY_USER"));
    }
    if(regex.test(repNewPassword)){
      return of(new UserResponse("ERROR_EMPTY_REPPASS"));
    }
    if(newPassword != repNewPassword){
      return of(new UserResponse('ERROR_NOT_EQ_PASS'));
    }

    return this.http.put<UserResponse>(this.url + '/' + user + '/change/password', null, {params:{newPassword:newPassword}});
  }
}
