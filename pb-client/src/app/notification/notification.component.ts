import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Notification, NotificationService, NotificationType } from '../service/notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit, OnDestroy {

  constructor(private notificationService: NotificationService) { }

  public notifications: Notification[] = [];
  public NotificationType = NotificationType;

  private subscriptions: Subscription[] = [];

  ngOnInit() {
    this.subscriptions.push(this.notificationService.$observable.subscribe(value => {
      this.notifications.unshift(value);
      setTimeout(() => {
        this.close(value);
      }, 10000);

    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  close(notification: Notification): void {
    var index = this.notifications.indexOf(notification);
    if (index > -1) {
      this.notifications.splice(index, 1);
    }
  }
}

