import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { ReviewsComponent } from './reviews/reviews.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ActivationComponent } from './register/activation/activation.component';
import { FormReviewsComponent } from './reviews/form-reviews/form-reviews.component';
import { UserReviewsComponent } from './reviews/user-reviews/user-reviews.component';
import { GamesComponent } from './games/games.component';
import { FormGamesComponent } from './games/form-games/form-games.component';
import { AdminGamesComponent } from './games/admin-games/admin-games.component';
import { AdminComponent } from './admin/admin.component';
import { CookieService } from 'ngx-cookie-service';
import { GuardGuard } from './guard.guard';
import { AdminGuardGuard } from './admin-guard.guard';
import { LoginGuardGuard } from './login-guard.guard';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { RippleModule } from 'primeng/ripple';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ChangePasswordComponent } from './login/change-password/change-password.component';
import { RequestChangePasswordComponent } from './login/request-change-password/request-change-password.component';

const routes : Routes = [
  {path:'', redirectTo:'/login', pathMatch:'full'},
  {path:'login', component:LoginComponent, canActivate:[LoginGuardGuard]},
  {path:'signup', component:RegisterComponent, canActivate:[LoginGuardGuard]},
  {path:'users/:username/activate', component:ActivationComponent},
  {path:'users/request/change/password', component:RequestChangePasswordComponent},
  {path:'users/:username/change/password', component:ChangePasswordComponent},
  {path:'home', component:HomeComponent, canActivate:[GuardGuard]},
  {path:'games', component:GamesComponent, canActivate:[GuardGuard]},
  {path:'games/:game/reviews', component:ReviewsComponent, canActivate:[GuardGuard]},
  {path:'games/:game/reviews/create', component:FormReviewsComponent, canActivate:[GuardGuard]},
  {path:'users/:username/reviews', component:UserReviewsComponent, canActivate:[GuardGuard]},
  {path:'users/:username/reviews/:id/edit', component:FormReviewsComponent, canActivate:[GuardGuard]},
  {path:'admin', component:AdminComponent, canActivate:[AdminGuardGuard]},
  {path:'admin/games', component:AdminGamesComponent, canActivate:[AdminGuardGuard]},
  {path:'admin/games/create', component:FormGamesComponent, canActivate:[AdminGuardGuard]},
  {path:'admin/games/:id/edit', component:FormGamesComponent, canActivate:[AdminGuardGuard]},
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ReviewsComponent,
    LoginComponent,
    RegisterComponent,
    FormReviewsComponent,
    UserReviewsComponent,
    GamesComponent,
    FormGamesComponent,
    AdminGamesComponent,
    AdminComponent,
    ActivationComponent,
    ChangePasswordComponent,
    RequestChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    ButtonModule,
    DropdownModule,
    BrowserAnimationsModule,
    RippleModule,
    InputTextModule,
    PasswordModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
