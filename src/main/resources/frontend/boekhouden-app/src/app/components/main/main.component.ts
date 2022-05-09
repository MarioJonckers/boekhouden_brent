import { Component, OnInit } from '@angular/core';
import { GeneralService } from 'src/app/services/general.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent implements OnInit {
  testMessage: string = '';

  constructor(private generalService: GeneralService) {}

  ngOnInit(): void {
    this.generalService.getTest().subscribe((data: any) => {
      this.testMessage = data;
    });
  }
}
