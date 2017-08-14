import { DashboardComponent } from './dashboard/dashboard.component';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { UserService } from './service/user.service';

import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { ChartsModule } from 'ng2-charts';
import { AccountService } from './service/account.service';
import { UserHttpService } from './service/user-http.service';
import { AddAccountComponent } from './add-account/add-account.component';
import { AccountDetailComponent } from './account-detail/account-detail.component';
import { NotificationComponent } from './notification/notification.component';
import { NotificationService } from './service/notification.service';
import { TitleComponent } from './title/title.component';

@NgModule({
  imports: [
    BrowserModule,
    ChartsModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      {
        path: 'dashboard',
        component: DashboardComponent
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'logout',
        component: LogoutComponent
      },
      {
        path: 'add-account',
        component: AddAccountComponent
      },
      {
        path: 'account/:id',
        component: AccountDetailComponent
      },
      {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full'
      }
    ])
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    LoginComponent,
    LogoutComponent,
    AddAccountComponent,
    AccountDetailComponent,
    NotificationComponent,
    TitleComponent,
  ],
  providers: [
    AccountService,
    NotificationService,
    UserHttpService,
    UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
