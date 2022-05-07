import { Component } from '@angular/core';
import { GeneralService } from './services/general.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'boekhouden-app';

  constructor(private generalService: GeneralService) {}

  ngOnInit(): void {
    this.generalService.getTest().subscribe((data: any) => {
      console.log(data);
    });
  }
}
