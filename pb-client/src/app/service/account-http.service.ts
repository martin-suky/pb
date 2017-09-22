import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UserHttpService } from './user-http.service';
import { CreateAccountRequest } from '../dto/create-account-request';
import { Account } from '../dto/account';


@Injectable()
export class AccountHttpService {

  constructor(private http: UserHttpService) { }

  public getAccounts():Observable<Account[]> {
    return this.http.get('/api/account');
  }

  public saveAccount(accountRequest: CreateAccountRequest): Observable<Account> {
    return this.http.post('/api/account', accountRequest);
  }

  getAccount(id: number): Observable<Account> {
    return this.http.get(`/api/account/${id}`);
  }
}
