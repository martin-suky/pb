import { NotificationService } from '../../service/notification.service';
import { Subscription } from 'rxjs/Subscription';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  private subscriptions: Subscription[] = [];

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private router: Router,
              private notificationService: NotificationService) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  public login(): void {
    if (this.loginForm.valid) {
      this.subscriptions.push(this.userService.login(this.loginForm.value.username, this.loginForm.value.password).subscribe(
        result => {
          if (result) {
            this.router.navigateByUrl('/');
          } else {
            this.notificationService.displayError('Login failed.');
          }
        }
      ));
    }
  }

}
