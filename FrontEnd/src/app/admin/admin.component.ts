import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

  username:string;

  constructor(private router:Router, private cookieService:CookieService){
    this.username = this.cookieService.get('token');
  }

  logout() : void {
    this.cookieService.delete('token');
    this.router.navigate(['/login']);
  }

}
