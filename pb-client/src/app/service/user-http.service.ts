import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { User } from '../dto/user';
import { Headers, Http, RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserHttpService {

  private user: User;

  constructor(private userService: UserService, private http: Http) {
    userService.$userObservable.subscribe(value => this.user = value);
  }

  public get(url: string, options?: RequestOptionsArgs ): Observable<Response> {
    if (!options) {
      options = {};
    }
    if (!options.headers) {
      options.headers = new Headers();
    }
    options.headers.append('Authorization', 'Basic ' + btoa(this.user.username + ':' + this.user.password));

    return this.http.get(url, options);
  }

}
