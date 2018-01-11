import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Transaction } from '../../dto/transaction';
import { SimpleDate } from '../../dto/simple-date';
import { TransactionService } from '../../service/transaction.service';
import { Account } from '../../dto/account';

@Component({
  selector: 'app-month',
  templateUrl: './month.component.html',
  styleUrls: ['./month.component.css']
})
export class MonthComponent implements OnInit {

  @Input()
  public account: Account;

  @Input()
  public month: SimpleDate;

  public transactions: Transaction[];
  public income: number = 0;
  public expense: number = 0;
  public balance: number = 0;

  constructor(private transactionService: TransactionService) { }

  ngOnInit() {
    this.transactionService.getTransactions(this.account, this.month)
      .subscribe(transactions => {
        this.reset();
        if (transactions) {
          this.transactions = transactions
          for (let transaction of this.transactions) {
            this.balance+= transaction.amount;
            if (transaction.amount > 0) {
              this.income += transaction.amount;
            } else {
              this.expense += transaction.amount;
            }
          }
        }
      });
  }

  public deleteTransaction(transaction: Transaction): void {
    this.transactionService.deleteTransaction(this.account, this.month, transaction);
  }

  private reset() {
    this.transactions = [];
    this.income = 0;
    this.expense = 0;
    this.balance = 0;
  }

}

