import { Component } from '@angular/core';
import { ActivationService } from './activation.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent {

  activated:boolean;
  username:string;

  constructor(private activationService:ActivationService, private activatedRoute:ActivatedRoute){
    this.activated = false;
    this.username = '';
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
          this.activationService.activate(user).subscribe(
            r =>{
              if(r.response == "OK") this.activated = true
            }
          );
        } 
      }
    );
  }

}
