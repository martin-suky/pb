import { Account } from '../dto/account';

export const AppReducers = {
  ACCOUNTS: 'accounts_reducer',
};

export interface AppState {
  accounts_reducer: Account[]
}
