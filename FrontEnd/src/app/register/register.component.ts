import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterService } from './register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  username:string;
  password:string;
  repeatedPassword:string;

  constructor(private registerService:RegisterService, private router:Router){
    this.username = '';
    this.password = '';
    this.repeatedPassword = '';
  }

  ngOnInit() : void {
  }

  register() : void {
    this.registerService.register(this.username, this.password, this.repeatedPassword).subscribe(
      //meter mensajes para EXISTS y ERROR
      s => {
        if(s.response == 'OK'){
          this.router.navigate(['/login']);
        }
      }
    );
  }

}
