import { Component, OnInit } from '@angular/core';
import { ActivationService } from './activation.service';
import { ActivatedRoute } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent implements OnInit{

  activated:boolean;
  username:string;

  constructor(private activationService:ActivationService, private activatedRoute:ActivatedRoute, 
              private cookieService:CookieService, private messageService:MessageService){
    this.activated = false;
    this.username = '';
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      r => {
        let user = r['username'];
        let token = r['token'];
        if(user){
          this.username = user;
          if(token) this.cookieService.set('token', token, {path:'/', secure:true});
          this.activationService.activate(user).subscribe(
            r =>{
              if(r.response == "OK"){
                this.activated = true;
                this.cookieService.deleteAll('/');
              } else if(r.response == 'NOT_FOUND'){
                this.messageService.clear();
                this.messageService.add({severity:'error', detail:'El usuario ' + this.username + ' no está registrado.'});
              }
            },
            error => {
              this.messageService.clear();
              this.messageService.add({severity:'error', detail:'Se ha producido un error interno. Inténtalo de nuevo más tarde.'});
            } 
          );
        } 
      }
    );
  }

}
