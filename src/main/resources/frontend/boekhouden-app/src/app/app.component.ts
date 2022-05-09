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
      header: 'Documentation',
    },
    {
      label: 'Get Started',
      route: '',
      iconClasses: 'fa fa-rocket',
    },
    {
      label: 'Multilevel',
      iconClasses: 'fa fa-share',
      children: [
        {
          label: 'Configuration 1',
          url: '//google.com',
          badges: [
            {
              label: '1',
              classes: 'badge--red',
            },
          ],
        },
        {
          header: 'Separator',
        },
        {
          label: 'Configuration 2',
          children: [
            {
              label: 'Configuration 1',
              url: '//google.com',
            },
          ],
        },
      ],
    },
    {
      label: 'Badges',
      iconClasses: 'fa fa-star',
      url: '//google.com',
      badges: [
        {
          label: 'new',
          classes: 'badge--red',
        },
        {
          label: '1',
          classes: 'badge--blue',
        },
      ],
    },
  ];

  constructor() {}
}
