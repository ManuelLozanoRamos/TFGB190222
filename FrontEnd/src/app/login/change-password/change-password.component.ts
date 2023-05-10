import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChangePasswordService } from './change-password.service';
import { CookieService } from 'ngx-cookie-service';

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
              private activatedRoute:ActivatedRoute, private cookieService:CookieService){
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
    this.changePasswordService.changePassword(this.username, this.newPassword, this.newPassword).subscribe(
      r => {
        if(r.response == 'OK'){
          this.cookieService.deleteAll('/');
          this.router.navigate(['/login']);
        } 
      }
    );
  }

}
