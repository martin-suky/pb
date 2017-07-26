import { Bank } from './bank';
import { User } from './user';

export interface Account {
  id: number,
  name: string,
  bank: Bank,
  owner: User,
  version: number
}
