import { Component } from '@angular/core';
import { Modes } from 'angular-sidebar-menu';
import { GeneralService } from './services/general.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'boekhouden-app';
  mode = Modes.EXPANDED;
  search = '';

  menu = [
    {
      label: 'Dashboard',
      route: '/',
      iconClasses: 'fa fa-tachometer',
    },
    {
      header: 'Klanten & Producten',
    },
    {
      label: 'Klanten',
      route: '/klanten',
      iconClasses: 'fa fa-users',
    },
    {
      label: 'Producten',
      route: '/producten',
      iconClasses: 'fa fa-cube',
    },
    {
      header: 'Documenten',
    },
    {
      label: 'Facturen',
      route: '/facturen',
      iconClasses: 'bi bi-file-earmark-ruled',
    },
    {
      label: 'Offertes',
      route: '/offertes',
      iconClasses: 'fa fa-rocket',
    },
  ];

  constructor() {}
}
