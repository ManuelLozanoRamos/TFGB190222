import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{
  username:string;

  constructor(private router:Router, private cookieService:CookieService){
    this.username = this.cookieService.get('token');
  }

  logout() : void {
    this.cookieService.delete('token');
    this.router.navigate(['/login']);
  }
}
