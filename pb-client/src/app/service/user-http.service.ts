import { Subscription } from 'rxjs/Subscription';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { User } from '../dto/user';
import { Headers, Http, RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserHttpService {

  constructor(private userService: UserService, private http: Http) {
  }

  public get<T>(url: string, options?: RequestOptionsArgs ): Observable<T> {
    return this.userService.$userObservable.filter(user => user != null).flatMap(user => {
      return <Observable<T>> <any> this.http.get(url, this.getOptions(options, user)).map((value: Response) => {return value.json()});
    }).first();
  }

  public post<T>(url: string, body: any, options?: RequestOptionsArgs ): Observable<T> {
    return this.userService.$userObservable.filter(user => user != null).flatMap(user => {
      return <Observable<T>> <any> this.http.post(url, body, this.getOptions(options, user)).map((value: Response) => {return value.json()});
    }).first();
    
  }

  private getOptions(options: RequestOptionsArgs, user: User): RequestOptionsArgs {
    if (!options) {
      options = {};
    }
    if (!options.headers) {
      options.headers = new Headers();
    }
    options.headers.append('Authorization', 'Basic ' + btoa(user.username + ':' + user.password));

    return options;
  }
}
