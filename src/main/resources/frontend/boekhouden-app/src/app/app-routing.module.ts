import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticlesComponent } from './components/articles/articles.component';
import { ClientsComponent } from './components/clients/clients.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { InvoiceComponent } from './components/invoice/invoice.component';

const routes: Routes = [
  {
    path: 'klanten',
    component: ClientsComponent,
  },
  {
    path: 'producten',
    component: ArticlesComponent,
  },
  {
    path: 'facturen',
    component: InvoiceComponent,
  },
  {
    path: '',
    component: DashboardComponent,
    pathMatch: 'full',
  },
  {
    path: '**',
    component: DashboardComponent,
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      relativeLinkResolution: 'legacy',
      anchorScrolling: 'enabled',
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
