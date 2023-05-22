import { Component } from '@angular/core';
import { RequestChangePasswordService } from './request-change-password.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-request-change-password',
  templateUrl: './request-change-password.component.html',
  styleUrls: ['./request-change-password.component.css']
})
export class RequestChangePasswordComponent {

  mail:string;

  constructor(private requestChangePassword:RequestChangePasswordService, private messageService:MessageService){
    this.mail = '';
  }

  request(){
    this.requestChangePassword.requestChangePassword(this.mail).subscribe(
      r => {
        if(r.response == 'OK'){
          this.messageService.clear();
          this.messageService.add({severity:'success', detail:'Correo electrónico enviado con éxito.'});
        } else if (r.response == 'ERROR_EMPTY_MAIL'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce la dirección de correo electrónico asociada a tu cuenta.'});
        } else if (r.response == 'NOT_VALIDATED'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'La dirección de correo asociada a la cuenta no ha sido validada. Revisa tu correo electrónico.'});
        } else if(r.response == 'NO_USER_EXISTS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'La dirección de correo no está asociada a ninguna cuenta registrada.'});
        }
      },
      error => {
        this.messageService.clear();
        this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'})
      }
    );
  }

}
