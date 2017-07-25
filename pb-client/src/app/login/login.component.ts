import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private router: Router) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  public login(): void {
    if (this.loginForm.valid) {
      this.userService.login(this.loginForm.value.username, this.loginForm.value.password);
      this.router.navigateByUrl('/');
    }
  }

}
