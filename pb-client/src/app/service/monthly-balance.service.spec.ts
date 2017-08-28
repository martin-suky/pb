import { TestBed, inject } from '@angular/core/testing';

import { MonthlyBalanceService } from './monthly-balance.service';

describe('MonthlyBalanceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MonthlyBalanceService]
    });
  });

  it('should be created', inject([MonthlyBalanceService], (service: MonthlyBalanceService) => {
    expect(service).toBeTruthy();
  }));
});
