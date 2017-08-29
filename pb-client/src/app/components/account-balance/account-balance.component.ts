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
export class AccountBalanceComponent implements OnInit, OnDestroy, OnChanges {

  @Input()
  public accounts: Account[] = [];

  public chartType = 'chartType';
  public lineChartData:Array<any> = [];
  public lineChartLabels:Array<string> = [];
  public lineChartLegend = true;
  public lineChartOptions:any = {
    responsive: true
  };

  private subscriptions: Subscription[] = [];

  constructor(private balanceService: MonthlyBalanceService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
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

  private prepareGraphData(allBalances: MonthlyBalance[][]): void {
    let date = new Date();
    let lowestYear: number = date.getFullYear();
    let lowestMonth: number = date.getMonth() + 1;
    let highestYear: number = date.getFullYear();
    let highestMonth: number = date.getMonth() + 1;
    let countOfGraphs: number = 0;
    this.lineChartData = [];

    for (let accountBalance of allBalances) {
      if (accountBalance.length == 0) {
        continue;
      } else {
        countOfGraphs ++;
        this.lineChartData.push({data: [], label: accountBalance[0].account.name});
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

    let indexYear = lowestYear;
    let indexMonth = lowestMonth;
    let highestLabel = `${highestYear}-${highestMonth}`;
    let indexLabel: string;
    let iteration: number = 0;
    do {
      if (iteration == countOfGraphs) {
        iteration = 0;
      }
      indexLabel = `${indexYear}-${indexMonth}`;
      this.lineChartLabels.push(indexLabel);
      for (let accountBalance of allBalances) {
        let balance = accountBalance.length > 0 ? accountBalance[0]: null;
        if (balance && balance.year == indexYear && balance.month == indexMonth) {
          this.lineChartData[iteration].data.push(balance.balance);
          accountBalance.shift();
        } else {
          this.lineChartData[iteration].data.push(0);
        }
      }
      iteration ++;
      indexMonth++;
      if (indexMonth == 13) {
        indexYear ++;
        indexMonth = 1;
      }
    } while (indexLabel < highestLabel);
    console.log(this.lineChartData);
  }



}
