import { Injectable } from '@angular/core';
import { User } from '../dto/user';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Http } from '@angular/http';
import { LoginRequest } from '../dto/login-request';

@Injectable()
export class UserService {

  private static CURRENT_USER_KEY: string = 'currentUser';
  private userSubject: Subject<User> = new BehaviorSubject(null);

  public $userObservable = this.userSubject.asObservable();

  constructor(private http: Http) {
    let stringUser = localStorage.getItem(UserService.CURRENT_USER_KEY);
    if (stringUser) {
      this.userSubject.next(JSON.parse(stringUser));
    }
  }

  public login(username: string, password: string): Observable<boolean> {
    let result: Subject<boolean> = new Subject();
    let loginRequest: LoginRequest = {username: username, password: password};
    this.http.post('/api/user/login', loginRequest).subscribe(next => {
        let user = {username: username, password: password};
        this.userSubject.next(user);
        localStorage.setItem(UserService.CURRENT_USER_KEY, JSON.stringify(user));
        result.next(true);
      },
      error => {
        result.next(false);
        localStorage.removeItem(UserService.CURRENT_USER_KEY);
        this.userSubject.next(null);
      });
    return result.asObservable();
  }

  public logout(): void {
    localStorage.removeItem(UserService.CURRENT_USER_KEY);
    this.userSubject.next(null);
  }
}
