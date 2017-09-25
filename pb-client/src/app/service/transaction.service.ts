import { Injectable } from '@angular/core';
import { TransactionHttpService } from './transaction-http.service';
import { MonthlyBalanceService } from './monthly-balance.service';
import { Observable } from 'rxjs/Observable';
import { Account } from '../dto/account';
import { UploadResponse } from '../dto/upload-response';
import { AccountService } from './account.service';

@Injectable()
export class TransactionService {

  constructor(private transactionHttpService: TransactionHttpService, private monthlyBalanceService: MonthlyBalanceService, private accountService: AccountService) { }

  public uploadTransactions(account: Account, file: File): Observable<UploadResponse> {
    let formData: FormData = new FormData();
    formData.append('file', file);
    const observable = this.transactionHttpService.uploadTransactions(account, file).publishLast().refCount();
    observable.subscribe(() => {
      this.monthlyBalanceService.updateBalances(account);
      this.accountService.updateAccount(account);
    });
    return observable;
  }

}
