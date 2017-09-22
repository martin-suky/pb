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


  private balancesSubject: BehaviorSubject<Map<number, BalanceData>> = new BehaviorSubject(new Map());

  public balances = this.balancesSubject.asObservable();

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
            result.forEach(data => map.set(data.account.id, data));
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
      this.balancesSubject.next(map);
    });
  }

  private fetchBalances(account): Observable<BalanceData> {
      return this.monthlyBalanceHttpService.getBalance(account).map(balances => new BalanceData(account, balances));
  }

}
