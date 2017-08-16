import { Transaction } from './../dto/transaction';
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-month',
  templateUrl: './month.component.html',
  styleUrls: ['./month.component.css']
})
export class MonthComponent implements OnInit, OnChanges {

  @Input()
  public month: Month;

  public income: number = 0;
  public expense: number = 0;
  public balance: number = 0;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.month && this.month.transactions) {
      for (let transaction of this.month.transactions) {
        this.balance+= transaction.amount;
        if (transaction.amount > 0) {
          this.income += transaction.amount;
        } else {
          this.expense += transaction.amount;
        }
      }
    }
  }

}


export interface Month {
 month: number,
 year: number,
 transactions: Transaction[]
}
