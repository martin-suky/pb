import { Account } from './../../dto/account';
import { MonthlyBalance } from './../../dto/monthly-balance';
import { Observable } from 'rxjs/Observable';
import { MonthlyBalanceService } from './../../service/monthly-balance.service';
import { Subscription } from 'rxjs/Subscription';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-total-balance',
  templateUrl: './total-balance.component.html',
  styleUrls: ['./total-balance.component.css']
})
export class TotalBalanceComponent implements OnInit {

  @Input()
  public accounts: Account[] = [];

  private chartType = 'line';
  private lineChartData:Array<any>;
  private lineChartLabels:Array<string>;
  private lineChartLegend = true;
  private lineChartOptions:any = {
    responsive: true,
    scales: {
      yAxes: [{
        id: 'default',
        type: 'linear'
      }]
    }
  };
  private prepared: boolean = false;
  private subscriptions: Subscription[] = [];

  constructor(private balanceService: MonthlyBalanceService) { }

  ngOnInit() {
    if (this.accounts) {
      let balanceCalls = [];
      this.accounts.forEach(account => {
        balanceCalls.push(this.balanceService.getBalance(account));
      });
      this.subscriptions.push(Observable.forkJoin(balanceCalls).subscribe(response => {
        this.prepareGraphData(response as MonthlyBalance[][]);
      }));
    }
  }

}
