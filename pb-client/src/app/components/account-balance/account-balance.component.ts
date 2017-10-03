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
        this.lineChartData.push({data: [], label: balance.account.name, yAxisID: 'default'});
        if (balance.balances.length > 0) {
          const balanceLowest = balance.balances[0];
          const balanceHighest = balance.balances[balance.balances.length - 1];
  
          if (lowestDate.compareTo(balanceLowest.date) > 0) {
            lowestDate = balanceLowest.date;
          }
          if (highestDate.compareTo(balanceHighest.date) < 0) {
            highestDate = balanceHighest.date;
          }
        }
      }
    });

    if (countOfGraphs == 0) {
      return;
    }

    do {
      this.lineChartLabels.push(lowestDate.toString());
      for (let i = 0; i < countOfGraphs; i++) {
        const accountBalance = displayedBalanceData[i];
        let balance = accountBalance.balances.length > 0 ? accountBalance.balances[0]: null;
        if (balance && lowestDate.equals(balance.date)) {
          this.lineChartData[i].data.push(balance.accumulatedBalance);
          accountBalance.balances.shift();
        } else {
          this.lineChartData[i].data.push(this.getLastBalance(this.lineChartData[i].data));
        }
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
