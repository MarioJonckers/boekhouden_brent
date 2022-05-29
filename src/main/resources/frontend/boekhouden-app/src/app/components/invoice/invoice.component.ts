import { Component, OnInit } from '@angular/core';
import { Invoice } from '../../classes/Invoice';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss'],
})
export class InvoiceComponent implements OnInit {
  constructor() {}

  originalInvoices: Invoice[] = [];
  invoices: Invoice[] = [];

  search: string = '';

  ngOnInit(): void {}

  filter() {}

  createInvoice() {}
}
