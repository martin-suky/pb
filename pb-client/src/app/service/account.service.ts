import { Injectable } from '@angular/core';
import { AccountHttpService } from './account-http.service';
import { Account } from '../dto/account';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { CreateAccountRequest } from '../dto/create-account-request';

@Injectable()
export class AccountService {

  private accountsSubject: BehaviorSubject<Account[]> = new BehaviorSubject([]);

  public accounts: Observable<Account[]> = this.accountsSubject.asObservable();

  constructor(private accountHttpService: AccountHttpService) {
    this.accountHttpService.getAccounts().subscribe(
      accounts => this.accountsSubject.next(accounts)
    );
  }

  public saveAccount(accountRequest: CreateAccountRequest): Observable<Account> {
    let observable = this.accountHttpService.saveAccount(accountRequest).publishLast().refCount();

    observable.subscribe(
    account => {
      const value = this.accountsSubject.getValue();
      value.push(account);
      this.accountsSubject.next(value);
      }
    );
    return observable;
  }
}
