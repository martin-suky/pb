import { Transaction } from '../dto/transaction';
import { TransactionSearch } from '../dto/transaction-search';
import { Account } from '../dto/account';
import { Injectable } from '@angular/core';
import { UserHttpService } from './user-http.service';
import { Observable } from 'rxjs/Observable';
import { UploadResponse } from '../dto/upload-response';

@Injectable()
export class TransactionHttpService {

  constructor(private http: UserHttpService) { }

  public uploadTransactions(account: Account, file: File, format: string): Observable<UploadResponse> {
    let formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('format', format);
    return this.http.post(`/api/account/${account.id}/transaction/upload`, formData);
  }

  public getTransactions(account: Account, search: TransactionSearch): Observable<Transaction[]> {
    return this.http.post(`/api/account/${account.id}/transaction/search`, search);
  }

}
