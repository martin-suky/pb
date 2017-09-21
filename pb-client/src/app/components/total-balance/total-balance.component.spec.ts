import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalBalanceComponent } from './total-balance.component';

describe('TotalBalanceComponent', () => {
  let component: TotalBalanceComponent;
  let fixture: ComponentFixture<TotalBalanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TotalBalanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TotalBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
