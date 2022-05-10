import { Component, OnInit } from '@angular/core';
import { GeneralService } from 'src/app/services/general.service';

@Component({
  selector: 'app-main',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  private today: Date = new Date();

  testMessage: string = '';
  selectedYear: number = this.today.getFullYear();
  availableYears: number[] = [2022, 2020, 2021];

  constructor(private generalService: GeneralService) {}

  ngOnInit(): void {
    this.generalService.getTest().subscribe((data: any) => {
      this.testMessage = data;
    });
    //TODO: Get years with data + get data for current year
  }

  updateSelectedYear(data: number) {
    this.selectedYear = data;
    //TODO: Get data for selectedYear
  }
}
