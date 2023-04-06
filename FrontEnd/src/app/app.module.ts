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

const routes : Routes = [
  {path:'', redirectTo:'/login', pathMatch:'full'},
  {path:'login', component:LoginComponent},
  {path:'register', component:RegisterComponent},
  {path:'home', component:HomeComponent},
  //{path:'games', component:GomeComponent},
  //{path:'reviews?game=:game', component:ReviewsComponent},
  {path:'reviews', component:ReviewsComponent},
  {path:'reviews/create', component:FormReviewsComponent},
  {path:'reviews/create?game=:game', component:FormReviewsComponent},
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
    UserReviewsComponent
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
