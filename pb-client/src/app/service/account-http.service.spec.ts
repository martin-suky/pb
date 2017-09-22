import { TestBed, inject } from '@angular/core/testing';

import { AccountHttpService } from './account-http.service';

describe('AccountHttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccountHttpService]
    });
  });

  it('should be created', inject([AccountHttpService], (service: AccountHttpService) => {
    expect(service).toBeTruthy();
  }));
});
