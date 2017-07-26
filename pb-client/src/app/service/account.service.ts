import { Account } from './../dto/account';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UserHttpService } from './user-http.service';
import { CreateAccountRequest } from '../dto/create-account-request';

@Injectable()
export class AccountService {

  constructor(private http: UserHttpService) { }

  public getAccounts(): Observable<Account[]> {
    return this.http.get('/api/account');
  }

  public saveAccount(accountRequest: CreateAccountRequest): Observable<Account> {
    return this.http.post('/api/account', accountRequest);
  }

  getAccount(id: number): Observable<Account> {
    return this.http.get(`/api/account/${id}`);
  }
}
