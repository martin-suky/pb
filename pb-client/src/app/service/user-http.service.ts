import { Subscription } from 'rxjs/Subscription';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { User } from '../dto/user';
import { Headers, Http, RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class UserHttpService {

  private user: User;
  private subscription: Subscription;

  constructor(private userService: UserService, private http: Http) {
    this.subscription = userService.$userObservable.subscribe(
      value => {
        this.user = value;
      }
    );
  }

  public get<T>(url: string, options?: RequestOptionsArgs ): Observable<T> {
    if (!this.user) {
      return Observable.of(null);
    }

    return <Observable<T>> <any> this.http.get(url, this.getOptions(options)).map((value: Response) => {return value.json()});
  }

  public post<T>(url: string, body: any, options?: RequestOptionsArgs ): Observable<T> {
    if (!this.user) {
      return Observable.of(null);
    }

    return <Observable<T>> <any> this.http.post(url, body, this.getOptions(options)).map((value: Response) => {return value.json()});
  }

  private getOptions(options: RequestOptionsArgs): RequestOptionsArgs {
    if (!options) {
      options = {};
    }
    if (!options.headers) {
      options.headers = new Headers();
    }
    options.headers.append('Authorization', 'Basic ' + btoa(this.user.username + ':' + this.user.password));

    return options;
  }
}
