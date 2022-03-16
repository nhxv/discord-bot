import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuildBotEditComponent } from './guild-bot-edit.component';

describe('GuildEditComponent', () => {
  let component: GuildBotEditComponent;
  let fixture: ComponentFixture<GuildBotEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuildBotEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuildBotEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
