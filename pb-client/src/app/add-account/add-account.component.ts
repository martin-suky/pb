import { Component, OnInit } from '@angular/core';
import { Bank } from '../dto/bank';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CreateAccountRequest } from '../dto/create-account-request';
import { AccountService } from '../service/account.service';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.css']
})
export class AddAccountComponent implements OnInit {

  accountForm: FormGroup;
  public banks:Bank[] = [Bank.MBANK];

  constructor(private fb: FormBuilder, private accountService: AccountService) { }

  ngOnInit() {
    this.accountForm = this.fb.group({
      name: new FormControl('', Validators.required),
      bank: new FormControl('', Validators.required),
    });
  }

  public saveAccount() {
    if (this.accountForm.valid) {
      let accountRequest: CreateAccountRequest = {
        name: this.accountForm.value.name,
        bank: this.accountForm.value.bank,
      };

      this.accountService.saveAccount(accountRequest).subscribe();
    }
  }

}
