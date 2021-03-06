import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { UserService } from '../../service/user.service';
import { User } from '../../dto/user';
import { Account } from '../../dto/account';
import { AccountService } from '../../service/account.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  public user: User;
  public totalCash: number = 0;
  public accounts: Account[];
  public activeTab: Tabs = Tabs.TOTAL_BALANCE;
  public Tabs = Tabs;

  private subscriptions: Subscription[] = [];

  constructor(private userService: UserService,
              private accountService: AccountService) {}

  ngOnInit(): void {
    this.subscriptions.push(this.userService.$userObservable.subscribe(
      value => this.user = value
    ));
    this.subscriptions.push(this.accountService.accounts.subscribe(
      accounts => {
          this.accounts = accounts;
          if (this.accounts) {
            this.accounts.forEach(account => this.totalCash += account.balance);
          }
      }
    ));
    }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  switchTab(tab: Tabs) {
    this.activeTab = tab;
  }

}

enum Tabs {
  TOTAL_BALANCE, BALANCE_PER_ACCOUNT, INCOMES_EXPENSES
}
