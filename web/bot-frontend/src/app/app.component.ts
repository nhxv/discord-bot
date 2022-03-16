import {Component, OnInit} from '@angular/core';
import {PrimeNGConfig} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'bot-frontend';

  constructor(private config: PrimeNGConfig) {}

  ngOnInit(): void {
    this.config.setTranslation({
        emptyMessage: '0 results',
    });
  }

}
