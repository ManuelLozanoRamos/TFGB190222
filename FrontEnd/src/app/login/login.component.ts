import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from '../app.component';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  username:string;
  password:string;

  constructor(private appComponent:AppComponent, private loginService:LoginService, 
              private router:Router, private cookieService:CookieService){
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
        //Comprobar mensajes de error y validaciones y mostrar mensaje
        b => {
          if(b.response == 'OK'){
            this.cookieService.set('token', b.token);
            this.router.navigate(['/home']);
          } else {
            //mostrar mensaje de que no son validas las credenciales y quitar la redireccion
            this.router.navigate(['/login']);
          }
        },
        error => console.log('Error interno: ' + error.status)
      );
    }
  }
}
