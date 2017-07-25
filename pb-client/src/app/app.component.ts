import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from './service/user.service';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  private static NOT_LOGGED_IN = 'Not logged in!';

  isLoggedIn: boolean = false;
  username: string = AppComponent.NOT_LOGGED_IN;

  private subscriptions: Subscription[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.subscriptions.push(this.userService.$userObservable.subscribe(
      value => {
        if (value) {
          this.isLoggedIn = true;
          this.username = value.username;
        } else {
          this.isLoggedIn = false;
          this.username = AppComponent.NOT_LOGGED_IN;
        }
      }
    ));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
