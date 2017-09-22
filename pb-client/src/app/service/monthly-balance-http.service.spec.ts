import { TestBed, inject } from '@angular/core/testing';

import { MonthlyBalanceHttpService } from './monthly-balance-http.service';

describe('MonthlyBalanceHttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MonthlyBalanceHttpService]
    });
  });

  it('should be created', inject([MonthlyBalanceHttpService], (service: MonthlyBalanceHttpService) => {
    expect(service).toBeTruthy();
  }));
});
