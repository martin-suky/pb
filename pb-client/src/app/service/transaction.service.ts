import { Injectable } from '@angular/core';
import { TransactionHttpService } from './transaction-http.service';
import { MonthlyBalanceService } from './monthly-balance.service';
import { Observable } from 'rxjs/Observable';
import { Account } from '../dto/account';
import { UploadResponse } from '../dto/upload-response';
import { AccountService } from './account.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Transaction } from '../dto/transaction';
import { SimpleDate } from '../dto/simple-date';
import { TransactionSearch } from '../dto/transaction-search';

@Injectable()
export class TransactionService {

  private transactionsByMonthSubject: BehaviorSubject<{[key: string]: Transaction[]}> = new BehaviorSubject({});

  constructor(private transactionHttpService: TransactionHttpService, private monthlyBalanceService: MonthlyBalanceService, private accountService: AccountService) { }

  public getTransactions(account: Account, date: SimpleDate): Observable<Transaction[]> {
    const key = `${account.id}-${date.toString()}`;
    const value = this.transactionsByMonthSubject.getValue();
    if (!(key in value)) {
      this.transactionHttpService.getTransactions(account, this.getSearch(date)).subscribe(this.transactionHandler(key));
    }
    return this.transactionsByMonthSubject.asObservable().map(o => o[key]);
  }

  public uploadTransactions(account: Account, file: File, format: string): Observable<UploadResponse> {
  
    const observable = this.transactionHttpService.uploadTransactions(account, file, format).publishLast().refCount();
    observable.subscribe(() => {
      this.monthlyBalanceService.updateBalances(account);
      this.accountService.updateAccount(account);
    });
    return observable;
  }

  private transactionHandler(key: string) {
    return (result: Transaction[]) => {
      const value = this.transactionsByMonthSubject.getValue();
      value[key] = result;
      this.transactionsByMonthSubject.next(value);
    };
  }

  private getSearch(date: SimpleDate): TransactionSearch {
    let from = new Date(date.year, date.month - 1, 1);
    let to = new Date(date.year, date.month, 0);
    return {
      from: from,
      to: to
    }
  }

}
