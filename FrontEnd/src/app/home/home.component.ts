import { Component } from '@angular/core';
import { AppComponent } from '../app.component';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  username:string;

  constructor(private appComponent:AppComponent){
    this.username = appComponent.username;
  }
}
