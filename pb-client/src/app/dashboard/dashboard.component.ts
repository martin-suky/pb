import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { UserService } from '../service/user.service';
import { User } from '../dto/user';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  public user: User;

  private subscriptions: Subscription[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.subscriptions.push(this.userService.$userObservable.subscribe(
      value => this.user = value
    ));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
