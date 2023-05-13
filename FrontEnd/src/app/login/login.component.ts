import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from '../app.component';
import { LoginService } from './login.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  username:string;
  password:string;

  constructor(private appComponent:AppComponent, private loginService:LoginService, 
              private router:Router, private cookieService:CookieService, private messageService:MessageService){
    this.username = '';
    this.password = '';
  }

  ngOnInit(): void {
  }

  login() : void{
    if(this.cookieService.get('token').includes('USER_SESSION')){
      this.router.navigate(['/home']);
    }
    else {
      this.loginService.isRegistered(this.username, this.password).subscribe(
        b => {
          if(b.response == 'OK'){
            this.cookieService.set('token', b.token, {path:'/', secure:true});
            this.router.navigate(['/home']);
          } else if(b.response == 'ERROR_EMPTY_USER'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce el nombre de usuario.'});
          } else if(b.response == 'ERROR_EMPTY_PASS'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'Introduce la contraseña.'});
          } else if(b.response == 'NO_USER_EXISTS' || b.response == 'BAD_PASS'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'El nombre de usuario o la contraseña no son correctos.'});
          } else if(b.response == 'NOT_VALIDATED'){
            this.messageService.clear();
            this.messageService.add({severity:'warn', detail:'La dirección de correo asociada a la cuenta no ha sido validada. Revisa tu correo electrónico.'});
          }
        },
        error => {
          this.messageService.clear();
          this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
        }
      );
    }
  }
}
