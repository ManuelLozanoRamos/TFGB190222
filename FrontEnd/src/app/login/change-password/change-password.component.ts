import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChangePasswordService } from './change-password.service';
import { CookieService } from 'ngx-cookie-service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit{

  username:string;
  newPassword:string;
  repNewPassword:string;

  constructor(private changePasswordService:ChangePasswordService, private router:Router, 
              private activatedRoute:ActivatedRoute, private cookieService:CookieService, private messageService:MessageService){
    this.username = '';
    this.newPassword = '';
    this.repNewPassword = '';
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      //Comprobar si hay errores y demás
      r => {
        let user = r['username'];
        let token = r['token'];
        if(user){
          this.username = user;
        } 
        if(token){
          this.cookieService.set('token', token, {path:'/',secure:true});
        }
      }
    );
  }


  changePassword(){
    this.changePasswordService.changePassword(this.username, this.newPassword, this.repNewPassword).subscribe(
      r => {
        if(r.response == 'OK'){
          this.messageService.clear();
          this.messageService.add({severity:'success', detail:'Contraseña cambiada con éxito.'});
          setTimeout(() => {
            this.cookieService.deleteAll('/');
            this.router.navigate(['/login']);
          }, 3000);
        } else if(r.response == 'ERROR_EMPTY_PASS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce la contraseña.'})
        } else if(r.response == 'ERROR_EMPTY_RPASS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Introduce la repetición de la contraseña.'})
        } else if(r.response == 'ERROR_NOT_EQ_PASS'){
          this.messageService.clear();
          this.messageService.add({severity:'warn', detail:'Ambas contraseñas deben ser iguales.'})
        } else if(r.response == 'NOT_FOUND'){
          this.messageService.clear();
          this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'})
        } 
      },
      error => {
        this.messageService.clear();
        this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
      }
    );
  }

}
