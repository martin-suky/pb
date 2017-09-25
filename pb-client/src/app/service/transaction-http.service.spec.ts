import { TestBed, inject } from '@angular/core/testing';

import { TransactionHttpService } from './transaction-http.service';

describe('TransactionHttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TransactionHttpService]
    });
  });

  it('should be created', inject([TransactionHttpService], (service: TransactionHttpService) => {
    expect(service).toBeTruthy();
  }));
});
