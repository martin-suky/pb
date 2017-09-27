import { Account } from './account';
import { SimpleDate } from './simple-date';
export interface MonthlyBalance {
    income: number,
    expense: number,
    balance: number,
    accumulatedBalance: number,
    year: number,
    month: number,
    date: SimpleDate,
    account: Account
}
