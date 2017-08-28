import { Observable } from 'rxjs/Observable';
import { MonthlyBalanceService } from './../service/monthly-balance.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { UserService } from '../service/user.service';
import { User } from '../dto/user';
import { AccountService } from '../service/account.service';
import { Account } from '../dto/account';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  public lineChartData:Array<any> = [
    {data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A'},
    {data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B'},
    {data: [18, 48, 77, 9, 100, 27, 40], label: 'Series C'}
  ];
  public lineChartLabels:Array<string> = [];
  public lineChartOptions:any = {
    responsive: true
  };
  public lineChartLegend:boolean = true;
  public lineChartType:string = 'line';

  //--------------------------------------------
  public user: User;
  public totalCash: number = 0;
  public accounts: Account[];

  private subscriptions: Subscription[] = [];

  constructor(private userService: UserService,
    private accountService: AccountService,
    private balanceService: MonthlyBalanceService) {}

  ngOnInit(): void {
    this.subscriptions.push(this.userService.$userObservable.subscribe(
      value => this.user = value
    ));
    this.subscriptions.push(this.accountService.getAccounts().subscribe(
      value => {
        this.accounts = value;
        this.fetchBalance();
      }));
    }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  private fetchBalance(): void {
    let balanceCalls = [];
    this.accounts.forEach(account => {
      balanceCalls.push(this.balanceService.getBalance(account));
    });
    this.subscriptions.push(Observable.forkJoin(balanceCalls).subscribe(response => {
      console.log(response);
    }));
  }

}
