import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserInfo } from 'src/app/register/userInfo';
import { GameResponse } from 'src/app/responses/game-response';
import { UserResponse } from 'src/app/responses/user-response';

@Injectable({
  providedIn: 'root'
})
export class ChangePasswordService {

  private url:string = 'http://localhost:8082/api/usuarios';

  constructor(private http:HttpClient) { }

  changePassword(user:string, newPassword:string, repNewPassword:string) : Observable<UserResponse>{
    const regex = new RegExp('^[ \t\n]*$');
    if(regex.test(newPassword)){
      return of(new UserResponse('ERROR_EMPTY_PASS', ''));
    }
    if(regex.test(repNewPassword)){
      return of(new UserResponse('ERROR_EMPTY_RPASS', ''));
    }
    if(newPassword != repNewPassword){
      return of(new UserResponse('ERROR_NOT_EQ_PASS', ''));
    }

    return this.http.put<UserResponse>(this.url + '/change/password', new UserInfo(user, newPassword, null));
  }
}
