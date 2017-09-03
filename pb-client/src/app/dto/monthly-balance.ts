import { Account } from './account';
export interface MonthlyBalance {
    income: number,
    expense: number,
    balance: number,
    accumulatedBalance: number,
    year: number,
    month: number,
    account: Account
}