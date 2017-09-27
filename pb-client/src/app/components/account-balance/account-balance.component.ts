import { SimpleDate } from './../../dto/simple-date';
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
          this.prepared = false;
          setTimeout(() => this.prepareGraphData(new Map().set(0, data)), 1);
        }
      ));
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }

  private prepareGraphData(data: Map<number, BalanceData>): void {
    let highestDate = SimpleDate.now();
    let lowestDate = highestDate;
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
        const balanceLowest = accountBalance.balances[0];
        const balanceHighest = accountBalance.balances[accountBalance.balances.length - 1];

        if (lowestDate.compareTo(balanceLowest.date) > 0) {
          lowestDate = balanceLowest.date;
        }
        if (highestDate.compareTo(balanceHighest.date) < 0) {
          highestDate = balanceHighest.date;
        }
      }
    }

    let iteration: number = 0;
    do {
      this.lineChartLabels.push(lowestDate.toString());
      for (let accountBalance of displayedBalanceData) {
        if (iteration == countOfGraphs) {
          iteration = 0;
        }
        let balance = accountBalance.balances.length > 0 ? accountBalance.balances[0]: null;
        if (balance && lowestDate.equals(balance.date)) {
          this.lineChartData[iteration].data.push(balance.accumulatedBalance);
          accountBalance.balances.shift();
        } else {
          this.lineChartData[iteration].data.push(this.getLastBalance(this.lineChartData[iteration].data));
        }
        iteration ++;
      }
      lowestDate = lowestDate.increment();
    } while (lowestDate.compareTo(highestDate) < 0);
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
