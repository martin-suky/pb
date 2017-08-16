import { Month } from './../month/month.component';
import { TransactionSearch } from './../dto/transaction-search';
import { TransactionService } from './../service/transaction.service';
import { Transaction } from './../dto/transaction';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { AccountService } from '../service/account.service';
import { Account } from '../dto/account';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  public account:Account;
  public months:Month[] = [];
  public month: number;
  public year: number;
  public currentMonth: number;
  public currentYear: number;

  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute, private accountService:AccountService, private transactionService: TransactionService) { }

  ngOnInit() {
    let date = new Date();
    this.month = date.getMonth() + 1;
    this.year = date.getFullYear();
    this.currentMonth = this.month;
    this.currentYear = this.year;
    this.subscriptions.push(this.route.params.map(params => +params['id']).subscribe(
      id => {
        this.subscriptions.push(this.accountService.getAccount(id).subscribe(account => {
          this.account = account;
          this.fetchInitialTransactions();
        }));
      }
    ));
  }

  public back(): void {
    this.months[2] = this.months[1];
    this.months[1] = this.months[0];
    this.months[0] = null;
    this.month --;
    if (this.month < 1) {
      this.month += 12;
      this.year--;
    }
    this.fetchMonth(0, this.month -2, this.year);
  }

  public forward(): void {
    this.months[0] = this.months[1];
    this.months[1] = this.months[2];
    this.months[2] = null;
    this.month ++;
    if (this.month > 12) {
      this.month -= 12;
      this.year++;
    }
    this.fetchMonth(2, this.month, this.year);
  }

  private fetchInitialTransactions(): void {
    this.fetchMonth(0, this.month -2, this.year);
    this.fetchMonth(1, this.month -1, this.year);
    this.fetchMonth(2, this.month, this.year);
  }

  private fetchMonth(index: number, month: number, year: number): void {
    let search = this.getSearch(month, year);
    this.subscriptions.push(this.transactionService.getTransactions(this.account, search).subscribe(result => {
      this.months[index] = {
        month: search.from.getMonth() + 1,
        year: search.from.getFullYear(),
        transactions: result
      };
    }));
  }

  private getSearch(month: number, year: number): TransactionSearch {
    if (month < 1) {
      month += 12;
      year--;
    }
    let from = new Date(year, month - 1, 1);
    let to = new Date(year, month, 0);
    return {
      from: from,
      to: to
    }
  }

}

