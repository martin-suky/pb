import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class NotificationService {

  private subject: Subject<Notification> = new Subject();
  public $observable: Observable<Notification> = this.subject.asObservable();

  constructor() { }

  private displayNotification(notification:Notification): void {
    console.log('displayed notification.', notification);
    this.subject.next(notification);
  }

  public displaySuccess(message: string): void {
    this.displayNotification({
      message: message,
      type: NotificationType.SUCCESS,
      title: 'Success'
    });
  }

  public displayError(message: string): void {
    this.displayNotification({
      message: message,
      type: NotificationType.DANGER,
      title: 'Error'
    });
  }

}

export interface Notification {
  title: string,
  message: string,
  type: NotificationType
}

export type NotificationType = 'success' | 'warning' | 'danger';
export const NotificationType = {
  SUCCESS: 'success' as NotificationType,
  WARNING: 'warning' as NotificationType,
  DANGER: 'danger' as NotificationType,
};
