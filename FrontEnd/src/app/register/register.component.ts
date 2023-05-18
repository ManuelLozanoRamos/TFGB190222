import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterService } from './register.service';
import { MessageService } from 'primeng/api';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  username:string;
  password:string;
  mail:string;
  repeatedPassword:string;

  constructor(private registerService:RegisterService, private router:Router, private messageService:MessageService){
    this.username = '';
    this.password = '';
    this.mail = '';
    this.repeatedPassword = '';
  }

  register() : void {
    this.registerService.register(this.username, this.password, this.repeatedPassword, this.mail).subscribe(
      s => {
        if(s.response == 'OK'){
          this.messageService.clear();
          this.messageService.add({severity:'success', detail:'Cuenta de usuario creada con éxito. Revisa tu correo para activarla y poder utilizarla.'})
          setTimeout(() => {this.router.navigate(['/login'])}, 3000);
        } else if(s.response == 'ERROR_EMPTY_USER'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce el nombre de usuario.'})
        } else if(s.response == 'ERROR_EMPTY_MAIL'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce la dirección de correo electrónico.'})
        } else if(s.response == 'ERROR_EMPTY_PASS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce la contraseña.'})
        } else if(s.response == 'ERROR_EMPTY_RPASS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce la repetición de la contraseña.'})
        } else if(s.response == 'ERROR_NOT_EQ_PASS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Ambas contraseñas deben ser iguales.'})
        } else if(s.response == 'USER_EXISTS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'El nombre de usuario ya está registrado.'})
        } else if(s.response == 'MAIL_EXISTS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'La dirección de correo electrónico ya está asociada a una cuenta registrada.'})
        }
      },
      error => {
        this.messageService.clear();
        this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
      }
    );
  }

}
