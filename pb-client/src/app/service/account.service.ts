import { Account } from './../dto/account';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UserHttpService } from './user-http.service';
import { CreateAccountRequest } from '../dto/create-account-request';
import { Store } from '@ngrx/store';
import { AppState } from '../reducer/reducers';
import { AccountAction } from '../reducer/account.reducer';


@Injectable()
export class AccountService {

  constructor(private http: UserHttpService, private store: Store<AppState>) { }

  public getAccounts(): void {
    this.http.get('/api/account').subscribe(
      accounts => this.store.dispatch({type: AccountAction.SET_ACCOUNTS, payload: accounts})
    );
  }

  public saveAccount(accountRequest: CreateAccountRequest): Observable<Account> {
    let observable: Observable<Account> = this.http.post('/api/account', accountRequest);
    return observable.map(value => {
      this.getAccounts();
      return value;
    });
  }

  getAccount(id: number): Observable<Account> {
    return this.http.get(`/api/account/${id}`);
  }
}
