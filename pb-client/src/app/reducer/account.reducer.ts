
import { Account } from '../dto/account';
import { Action } from '@ngrx/store';

export const AccountAction = {
  SET_ACCOUNTS: 'SET_ACCOUNTS',
};

export function accountReducer(accounts: Account[] = null, action: Action): Account[] {
  switch (action.type) {
    case AccountAction.SET_ACCOUNTS:
      return action.payload;
    default:
      return accounts;
  }
}
