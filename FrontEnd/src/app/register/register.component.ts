import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterService } from './register.service';

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

  constructor(private registerService:RegisterService, private router:Router){
    this.username = '';
    this.password = '';
    this.mail = '';
    this.repeatedPassword = '';
  }

  register() : void {
    this.registerService.register(this.username, this.password, this.repeatedPassword, this.mail).subscribe(
      //meter mensajes para EXISTS y ERROR
      s => {
        if(s.response == 'OK'){
          this.router.navigate(['/login']);
        }
      }
    );
  }

}
