import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { CookieOptions, CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GuardGuard implements CanActivate {

  constructor(private router:Router, private cookieService:CookieService){

  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const cookie = this.cookieService.check('token');
    if(!cookie || !this.cookieService.get('token').includes('USER_SESSION')){
      this.cookieService.deleteAll('/');
      this.router.navigate(['/login']);
      return false;
    } 
    else {
      return true;
    }
  }
  
}
