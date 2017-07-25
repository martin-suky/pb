import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { UserHttpService } from './user-http.service';

@Injectable()
export class AccountService {

  constructor(private http: UserHttpService) { }

  public getAccounts(): Observable<Account> {
    return this.http.get('/api/account');
  }

}
