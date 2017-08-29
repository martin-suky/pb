import { MonthlyBalanceService } from './service/monthly-balance.service';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { UserService } from './service/user.service';

import 'rxjs/add/observable/forkJoin';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { ChartsModule } from 'ng2-charts';
import { AccountService } from './service/account.service';
import { UserHttpService } from './service/user-http.service';
import { AddAccountComponent } from './components/add-account/add-account.component';
import { AccountDetailComponent } from './components/account-detail/account-detail.component';
import { NotificationComponent } from './components/notification/notification.component';
import { NotificationService } from './service/notification.service';
import { TitleComponent } from './components/title/title.component';
import { UploadComponent } from './components/upload/upload.component';
import { TransactionService } from './service/transaction.service';
import { MonthComponent } from './components/month/month.component';
import { AccountBalanceComponent } from './components/account-balance/account-balance.component';

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
        path: 'upload/:id',
        component: UploadComponent
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
    UploadComponent,
    MonthComponent,
    AccountBalanceComponent,
  ],
  providers: [
    AccountService,
    MonthlyBalanceService,
    NotificationService,
    TransactionService,
    UserHttpService,
    UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
