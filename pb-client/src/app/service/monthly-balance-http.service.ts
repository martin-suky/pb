import { MonthlyBalance } from './../dto/monthly-balance';
import { Observable } from 'rxjs/Observable';
import { Account } from './../dto/account';
import { UserHttpService } from './user-http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class MonthlyBalanceHttpService {

  constructor(private http: UserHttpService) { }

  public getBalance(account: Account): Observable<MonthlyBalance[]> {
    return this.http.get(`/api/account/${account.id}/balance`);
  }

}
