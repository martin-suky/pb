import { Bank } from './bank';
import { User } from './user';

export interface Account {
  id: number,
  name: string,
  balance: number,
  bank: Bank,
  owner: User,
  version: number
}
