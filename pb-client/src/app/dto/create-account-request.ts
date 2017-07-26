import { Bank } from './bank';

export interface CreateAccountRequest {
  name: string,
  bank: Bank
}
