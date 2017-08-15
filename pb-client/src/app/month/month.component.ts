import { Transaction } from './../dto/transaction';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-month',
  templateUrl: './month.component.html',
  styleUrls: ['./month.component.css']
})
export class MonthComponent implements OnInit {

  @Input()
  public month: Month;

  constructor() { }

  ngOnInit() {
  }

}


export interface Month {
 month: number,
 year: number,
 transactions: Transaction[] 
}
