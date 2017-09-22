import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Account } from '../../dto/account';
import { Observable } from 'rxjs/Observable';
import { MonthlyBalance } from '../../dto/monthly-balance';
import { BalanceData, MonthlyBalanceService } from '../../service/monthly-balance.service';

@Component({
  selector: 'app-account-balance',
  templateUrl: './account-balance.component.html',
  styleUrls: ['./account-balance.component.css']
})
export class AccountBalanceComponent implements OnInit, OnDestroy {

  @Input()
  public accounts: Account[] = [];

  private accountIds: number[] = [];
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

  constructor(private balanceService: MonthlyBalanceService) {
  }

  ngOnInit() {
    if (this.accounts && this.accounts.length > 0) {
      this.accountIds = this.accounts.map(a => a.id);
      this.subscriptions.push(this.balanceService.balances.subscribe(
        data => this.prepareGraphData(data)
      ));
    } else {
      this.accountIds = [0];
      this.subscriptions.push(this.balanceService.totalBalance.subscribe(
        data => {
          this.prepareGraphData(new Map().set(0, data));
        }
      ));
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  private prepareGraphData(data: Map<number, BalanceData>): void {
    let date = new Date();
    let lowestYear: number = date.getFullYear();
    let lowestMonth: number = date.getMonth() + 1;
    let highestYear: number = date.getFullYear();
    let highestMonth: number = date.getMonth() + 1;
    let countOfGraphs: number = 0;
    let displayedBalanceData: BalanceData[] = [];
    this.lineChartData = [];
    this.lineChartLabels = [];

    data.forEach(balance => {
      if (this.accountIds.indexOf(balance.account.id) > -1) {
        displayedBalanceData.push(balance.clone());
        countOfGraphs++;
      }
    });

    if (countOfGraphs == 0) {
      return;
    }

    for (let accountBalance of displayedBalanceData) {
      this.lineChartData.push({data: [], label: accountBalance.account.name, yAxisID: 'default'});
      if (accountBalance.balances.length > 0) {
        const accountLowest = `${accountBalance.balances[0].year}-${accountBalance.balances[0].month}`;
        const accountHighest = `${accountBalance.balances[accountBalance.balances.length - 1].year}-${accountBalance.balances[accountBalance.balances.length - 1].month}`;
        if (`${lowestYear}-${lowestMonth}` > accountLowest) {
          lowestYear = accountBalance.balances[0].year;
          lowestMonth = accountBalance.balances[0].month;
        }
        if (`${highestYear}-${highestMonth}` < accountHighest) {
          highestYear = accountBalance.balances[accountBalance.balances.length - 1].year;
          highestMonth = accountBalance.balances[accountBalance.balances.length - 1].month;
        }
      }
    }

    let indexYear = lowestYear;
    let indexMonth = lowestMonth;
    let highestLabel = `${highestYear}-${highestMonth}`;
    let indexLabel: string;
    let iteration: number = 0;
    do {
      indexLabel = `${indexYear}-${indexMonth}`;
      this.lineChartLabels.push(indexLabel);
      for (let accountBalance of displayedBalanceData) {
        if (iteration == countOfGraphs) {
          iteration = 0;
        }
        let balance = accountBalance.balances.length > 0 ? accountBalance.balances[0]: null;
        if (balance && balance.year == indexYear && balance.month == indexMonth) {
          this.lineChartData[iteration].data.push(balance.accumulatedBalance);
          accountBalance.balances.shift();
        } else {
          this.lineChartData[iteration].data.push(this.getLastBalance(this.lineChartData[iteration].data));
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
  }


  private getLastBalance(data: any[]): number {
    if (data.length > 0) {
      return data[data.length - 1];
    } else {
      return 0;
    }
  }
}
