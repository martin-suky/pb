import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { UserService } from '../../service/user.service';
import { User } from '../../dto/user';
import { AccountService } from '../../service/account.service';
import { Account } from '../../dto/account';
import { Store } from '@ngrx/store';
import { AppReducers, AppState } from '../../reducer/reducers';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  public user: User;
  public totalCash: number = 0;
  public accounts: Account[];

  private subscriptions: Subscription[] = [];

  constructor(private userService: UserService,
              private accountService: AccountService,
              private store: Store<AppState>) {}

  ngOnInit(): void {
    this.subscriptions.push(this.userService.$userObservable.subscribe(
      value => this.user = value
    ));
    this.subscriptions.push(this.store.select<Account[]>(AppReducers.ACCOUNTS).subscribe(
      accounts => {
        if (accounts !== null) {
          this.accounts = accounts;
          this.accounts.forEach(account => this.totalCash += account.balance);
        } else {
          this.accountService.getAccounts();
        }
      }
    ));
    }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
