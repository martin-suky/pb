import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Account } from '../../dto/account';
import { AccountService } from '../../service/account.service';
import { SimpleDate } from '../../dto/simple-date';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit, OnDestroy {

  public account: Account;
  public months: SimpleDate[] = [];
  public activeDate: SimpleDate;
  public currentDate: SimpleDate;

  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute, private accountService: AccountService) {
  }

  ngOnInit() {
    this.activeDate = SimpleDate.now();
    this.currentDate = this.activeDate;
    this.subscriptions.push(this.route.params.map(params => +params['id']).subscribe(
      id => {
        this.subscriptions.push(this.accountService.getAccount(id).subscribe(account => {
          if (account) {
            this.account = account;
            this.fetchInitialTransactions();
          }
        }));
      }
    ));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  public back(): void {
    this.months[2] = this.months[1];
    this.months[1] = this.months[0];
    this.months[0] = null;
    this.activeDate = this.activeDate.decrement();
    this.fetchMonth(0, this.activeDate.decrementTimes(2));
  }

  public forward(): void {
    this.months[0] = this.months[1];
    this.months[1] = this.months[2];
    this.months[2] = null;
    this.activeDate = this.activeDate.increment();
    this.fetchMonth(2, this.activeDate);
  }

  private fetchInitialTransactions(): void {
    this.fetchMonth(0, this.activeDate.decrementTimes(2));
    this.fetchMonth(1, this.activeDate.decrement());
    this.fetchMonth(2, this.activeDate);
  }

  private fetchMonth(index: number, date: SimpleDate): void {
    this.months[index] = date;
  }

}

