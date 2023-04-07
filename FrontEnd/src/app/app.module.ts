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
import { FormReviewsComponent } from './reviews/form-reviews/form-reviews.component';
import { UserReviewsComponent } from './reviews/user-reviews/user-reviews.component';
import { GamesComponent } from './games/games.component';
import { FormGamesComponent } from './games/form-games/form-games.component';
import { AdminGamesComponent } from './games/admin-games/admin-games.component';

const routes : Routes = [
  {path:'', redirectTo:'/login', pathMatch:'full'},
  {path:'login', component:LoginComponent},
  {path:'register', component:RegisterComponent},

  {path:'home', component:HomeComponent},

  {path:'games', component:GamesComponent},
  {path:'games/create', component:FormGamesComponent},
  {path:'games/:id/edit', component:FormGamesComponent},

  {path:'games/:id/reviews', component:ReviewsComponent},
  {path:'games/:id/reviews/create', component:FormReviewsComponent},
  //{path:'reviews/create?game=:game', component:FormReviewsComponent},

  {path:':username/reviews', component:UserReviewsComponent},
  {path:':username/reviews/:id/edit', component:FormReviewsComponent},
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
    AdminGamesComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
