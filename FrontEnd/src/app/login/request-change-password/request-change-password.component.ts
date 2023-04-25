import { Component } from '@angular/core';
import { RequestChangePasswordService } from './request-change-password.service';

@Component({
  selector: 'app-request-change-password',
  templateUrl: './request-change-password.component.html',
  styleUrls: ['./request-change-password.component.css']
})
export class RequestChangePasswordComponent {

  mail:string;

  constructor(private requestChangePassword:RequestChangePasswordService){
    this.mail = '';
  }

  request(){
    this.requestChangePassword.requestChangePassword(this.mail).subscribe(
      r => {
        if(r.response == 'OK'){
          //mostrar mensaje de correo enviado
        } else {
          //mostrar mensajes de que no enviado segun corresponda
        }
      }
    );
  }

}
