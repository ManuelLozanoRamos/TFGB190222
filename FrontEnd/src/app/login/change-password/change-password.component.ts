import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChangePasswordService } from './change-password.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit{

  username:string;
  newPassword:string;
  repNewPassword:string;

  constructor(private changePasswordService:ChangePasswordService, private router:Router, private activatedRoute:ActivatedRoute){
    this.username = '';
    this.newPassword = '';
    this.repNewPassword = '';
  }

  ngOnInit(): void {
    this.activate();
  }

  activate(){
    this.activatedRoute.params.subscribe(
      //Comprobar si hay errores y demás
      r => {
        let user=r['username'];
        if(user){
          this.username = user;
        } 
      }
    );
  }

  changePassword(){
    this.changePasswordService.changePassword(this.username, this.newPassword, this.newPassword).subscribe(
      r => {
        if(r.response == 'OK'){
          this.router.navigate(['/login']);
        } 
      }
    );
  }

}
