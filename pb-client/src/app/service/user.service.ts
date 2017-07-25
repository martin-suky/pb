import { Injectable } from '@angular/core';
import { User } from '../dto/user';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Http } from '@angular/http';
import { LoginRequest } from '../dto/login-request';

@Injectable()
export class UserService {

  private userSubject: Subject<User> = new BehaviorSubject(null);

  public $userObservable = this.userSubject.asObservable();

  constructor(private http: Http) { }

  public login(username: string, password: string): Observable<boolean> {
    let result: Subject<boolean> = new Subject();
    let loginRequest: LoginRequest = {username: username, password: password};
    this.http.post("/api/user/login", loginRequest).subscribe(next => {
      this.userSubject.next({username: username, password: password});
      result.next(true);
    },
      error => {
      result.next(false);
      });
    return result.asObservable();
  }

}
