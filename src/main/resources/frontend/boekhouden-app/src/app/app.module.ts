import { HttpClientModule } from '@angular/common/http';
import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SidebarMenuModule } from 'angular-sidebar-menu';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  NumberArray,
  Pad,
  RandomizeUrl,
  Round,
  SortArray,
  Trim,
} from './services/pipes.service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import {
  ClientsComponent,
  NgbdSortableHeader,
} from './components/clients/clients.component';
import {
  ArticlesComponent,
  NgbdArticleSortableHeader,
} from './components/articles/articles.component';
import { FormsModule } from '@angular/forms';
import { UpdateClientComponent } from './components/clients/update-client/update-client.component';
import { ToastsContainer } from './toast/toast-container.component';
import { UpdateArticleComponent } from './components/articles/update-article/update-article.component';
import { InvoiceComponent } from './components/invoice/invoice.component';
import { UpdateInvoiceComponent } from './components/invoice/update-invoice/update-invoice.component';
import { registerLocaleData } from '@angular/common';

import localeNl from '@angular/common/locales/nl';
registerLocaleData(localeNl);
@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    Pad,
    Round,
    RandomizeUrl,
    NumberArray,
    SortArray,
    Trim,
    ClientsComponent,
    ArticlesComponent,
    NgbdArticleSortableHeader,
    NgbdSortableHeader,
    UpdateClientComponent,
    ToastsContainer,
    UpdateArticleComponent,
    InvoiceComponent,
    UpdateInvoiceComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SidebarMenuModule,
    BrowserAnimationsModule,
    NgbModule,
    FormsModule,
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'nl' }],
  bootstrap: [AppComponent],
})
export class AppModule {}
