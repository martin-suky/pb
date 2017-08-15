import { Injectable } from '@angular/core';
import { UserHttpService } from './user-http.service';
import { Account } from '../dto/account';
import { Observable } from 'rxjs/Observable';
import { UploadResponse } from '../dto/upload-response';

@Injectable()
export class TransactionService {

  constructor(private http: UserHttpService) { }

  public uploadTransactions(account: Account, file: File): Observable<UploadResponse> {
    let formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post(`/api/account/${account.id}/transaction/upload`, formData);
  }

}
