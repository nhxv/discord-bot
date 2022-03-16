import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuildBotEditPageComponent } from './guild-bot-edit-page.component';

describe('GuildEditPageComponent', () => {
  let component: GuildBotEditPageComponent;
  let fixture: ComponentFixture<GuildBotEditPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuildBotEditPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuildBotEditPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
