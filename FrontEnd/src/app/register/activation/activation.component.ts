import { Component, OnInit } from '@angular/core';
import { ActivationService } from './activation.service';
import { ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent implements OnInit{

  activated:boolean;
  username:string;

  constructor(private activationService:ActivationService, private activatedRoute:ActivatedRoute, private cookieService:CookieService){
    this.activated = false;
    this.username = '';
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      //Comprobar si hay errores y demás
      r => {
        let user = r['username'];
        let token = r['token'];
        if(user){
          this.username = user;
          if(token) this.cookieService.set('token', token);
          this.activationService.activate(user).subscribe(
            r =>{
              if(r.response == "OK"){
                this.activated = true;
                this.cookieService.delete('token');
              } 
            }
          );
        } 
      }
    );
  }

}
