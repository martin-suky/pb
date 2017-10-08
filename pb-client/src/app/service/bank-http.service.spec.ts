import { TestBed, inject } from '@angular/core/testing';

import { BankHttpService } from './bank-http.service';

describe('BankHttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BankHttpService]
    });
  });

  it('should be created', inject([BankHttpService], (service: BankHttpService) => {
    expect(service).toBeTruthy();
  }));
});
