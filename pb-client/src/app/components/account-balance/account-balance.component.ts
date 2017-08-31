import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Account } from '../../dto/account';
import { Observable } from 'rxjs/Observable';
import { MonthlyBalanceService } from '../../service/monthly-balance.service';
import { MonthlyBalance } from '../../dto/monthly-balance';

@Component({
  selector: 'app-account-balance',
  templateUrl: './account-balance.component.html',
  styleUrls: ['./account-balance.component.css']
})
export class AccountBalanceComponent implements OnInit, OnDestroy {

  @Input()
  public accounts: Account[] = [];

  public chartType = 'line';
  public lineChartData:Array<any>;
  public lineChartLabels:Array<string>;
  public lineChartLegend = true;
  public lineChartOptions:any = {
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

  constructor(private balanceService: MonthlyBalanceService) {
  }

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

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  private prepareGraphData(allBalances: MonthlyBalance[][]): void {
    let date = new Date();
    let lowestYear: number = date.getFullYear();
    let lowestMonth: number = date.getMonth() + 1;
    let highestYear: number = date.getFullYear();
    let highestMonth: number = date.getMonth() + 1;
    let countOfGraphs: number = 0;
    this.lineChartData = [];
    this.lineChartLabels = [];

    for (let accountBalance of allBalances) {
      if (accountBalance.length == 0) {
        continue;
      } else {
        countOfGraphs ++;
        this.lineChartData.push({data: [], label: accountBalance[0].account.name, yAxisID: 'default'});
      }
      if (`${lowestYear}-${lowestMonth}` > `${accountBalance[0].year}-${accountBalance[0].month}`) {
        lowestYear = accountBalance[0].year;
        lowestMonth = accountBalance[0].month;
      }
      if (`${highestYear}-${highestMonth}` < `${accountBalance[accountBalance.length - 1].year}-${accountBalance[accountBalance.length - 1].month}`) {
        highestYear = accountBalance[accountBalance.length - 1].year;
        highestMonth = accountBalance[accountBalance.length - 1].month;
      }
    }

    if (countOfGraphs == 0) {
      return;
    }

    let indexYear = lowestYear;
    let indexMonth = lowestMonth;
    let highestLabel = `${highestYear}-${highestMonth}`;
    let indexLabel: string;
    let iteration: number = 0;
    do {
      indexLabel = `${indexYear}-${indexMonth}`;
      this.lineChartLabels.push(indexLabel);
      for (let accountBalance of allBalances) {
        if (iteration == countOfGraphs) {
          iteration = 0;
        }
        let balance = accountBalance.length > 0 ? accountBalance[0]: null;
        if (balance && balance.year == indexYear && balance.month == indexMonth) {

          let acumulatedBalance = this.getAcumulatedBalance(this.lineChartData[iteration].data, balance.balance);
          this.lineChartData[iteration].data.push(acumulatedBalance);
          accountBalance.shift();
        } else {
          this.lineChartData[iteration].data.push(this.getAcumulatedBalance(this.lineChartData[iteration].data, 0));
        }
        iteration ++;
      }
      indexMonth++;
      if (indexMonth == 13) {
        indexYear ++;
        indexMonth = 1;
      }
    } while (indexLabel < highestLabel);
    this.prepared = true;
    console.log(this.lineChartData);
  }


  private getAcumulatedBalance(data: any[], balance: number): number {
    if (data.length > 0) {
      return data[data.length - 1] + balance;
    } else {
      return balance;
    }
  }
}
