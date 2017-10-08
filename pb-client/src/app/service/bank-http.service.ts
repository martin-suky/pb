import { BankFormat } from './../dto/bank-format';
import { Observable } from 'rxjs/Observable';
import { Bank } from './../dto/bank';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable()
export class BankHttpService {

  constructor(private http: Http) { }

  public getBankFormats(bank: Bank): Observable<BankFormat[]> {
    return <Observable<BankFormat[]>> <any> this.http.get(`/api/bank/${bank}`).map(r => r.json());
  }

}
