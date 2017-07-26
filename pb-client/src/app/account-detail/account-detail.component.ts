import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { AccountService } from '../service/account.service';
import { Account } from '../dto/account';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  public account:Account;

  private subscriptions: Subscription[] = [];

  constructor(private route: ActivatedRoute, private accountService:AccountService) { }

  ngOnInit() {
    this.subscriptions.push(this.route.params.map(params => +params['id']).subscribe(
      id => {
        this.subscriptions.push(this.accountService.getAccount(id).subscribe(account => this.account = account));
      }
    ));
  }

}
