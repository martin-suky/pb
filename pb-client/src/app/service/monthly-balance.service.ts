import { SimpleDate } from './../dto/simple-date';
import { Injectable } from '@angular/core';
import { AccountService } from './account.service';
import { MonthlyBalanceHttpService } from './monthly-balance-http.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Account } from '../dto/account';
import { MonthlyBalance } from '../dto/monthly-balance';
import { Observable } from 'rxjs/Observable';

export class BalanceData {
  constructor(public account: Account,
  public balances: MonthlyBalance[]) {}

  public clone() {
    let balances = [].concat(this.balances);
    return new BalanceData(this.account, balances);
  }
}

@Injectable()
export class MonthlyBalanceService {

  private totalBalanceSubject: BehaviorSubject<BalanceData> = new BehaviorSubject(new BalanceData({id: 0, name: "Total"}, []));
  private balancesSubject: BehaviorSubject<Map<number, BalanceData>> = new BehaviorSubject(new Map());

  public balances = this.balancesSubject.asObservable();
  public totalBalance = this.totalBalanceSubject.asObservable();

  constructor(private accountService: AccountService, private monthlyBalanceHttpService: MonthlyBalanceHttpService) {
    this.accountService.accounts.subscribe(
      accounts => {
        const map = this.balancesSubject.getValue();
        let observables: Observable<BalanceData>[] = [];
        for (let account of accounts) {
          if (!map.has(account.id)) {
            observables.push(this.fetchBalances(account));
          }
        }
        if (observables.length > 0) {
          Observable.forkJoin(observables).subscribe(result => {
            const map = this.balancesSubject.getValue();
            result.forEach(data => {map.set(data.account.id, data);});
            this.updateTotalBalance(map);
            this.balancesSubject.next(map);
          })
        }
      }
    )
  }

  public updateBalances(account: Account): void {
    this.fetchBalances(account).subscribe(data => {
      const map = this.balancesSubject.getValue();
      map.set(data.account.id, data);
      this.updateTotalBalance(map);
      this.balancesSubject.next(map);
    });
  }

  private updateTotalBalance(map: Map<number, BalanceData>): void {
    let highestDate = SimpleDate.now();

    let {accounts, lowestDate} = this.getAccountsAndLowestDate(map);

    const account: Account = {id: 0, name: "Total"};
    const result: BalanceData = new BalanceData(account, []);

    let previousBalance: MonthlyBalance= this.getNewBalance();

    while (lowestDate.compareTo(highestDate) <= 0) {
      const balance: MonthlyBalance = this.getNewBalance();
      balance.account = account;
      balance.accumulatedBalance = previousBalance.accumulatedBalance;
      this.setDates(balance, lowestDate);

      accounts.forEach(value => {
        let currentBalance = value.balances.length > 0 ? value.balances[0]: null;
        if (currentBalance && lowestDate.equals(currentBalance.date)) {
          balance.income = this.incrementAndRound(balance.income, currentBalance.income);
          balance.expense = this.incrementAndRound(balance.expense, currentBalance.expense);
          balance.balance = this.incrementAndRound(balance.balance, currentBalance.balance);
          balance.accumulatedBalance = this.incrementAndRound(balance.accumulatedBalance, currentBalance.balance);
          value.balances.shift();
        }
      });

      result.balances.push(balance);
      previousBalance = balance;
      lowestDate = lowestDate.increment();
    }

    this.totalBalanceSubject.next(result);
  }

  private getAccountsAndLowestDate(map: Map<number, BalanceData>): {accounts: BalanceData[], lowestDate: SimpleDate} {
    const accounts: BalanceData[] = [];
    let lowestDate = SimpleDate.now();
    map.forEach(value => {
      accounts.push(value.clone());
      if (value.balances && value.balances.length > 0
         && (lowestDate.compareTo(value.balances[0].date) > 0)) {
        lowestDate = value.balances[0].date;
      }
    });
    return {
      accounts: accounts,
      lowestDate: lowestDate
    }
  }

  private setDates(balance: MonthlyBalance, date: SimpleDate): void {
    balance.year = date.year;
    balance.month = date.month;
    balance.date = date;
  }

  private incrementAndRound(a: number, b: number): number {
    return Math.round((a+b)*100)/100;
  }

  private fetchBalances(account): Observable<BalanceData> {
      return this.monthlyBalanceHttpService.getBalance(account).map(balances => new BalanceData(account, balances));
  }

  private getNewBalance(): MonthlyBalance {
    return {
      income: 0,
      expense: 0,
      balance: 0,
      accumulatedBalance: 0,
      year: 0,
      month: 0,
      date: null,
      account: null
    };
  }

}
