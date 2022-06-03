import { Component, OnInit } from '@angular/core';
import { InvoiceLine } from 'src/app/classes/InvoiceLine';
import { InvoiceService } from 'src/app/services/invoice.service';
import { ToastService } from 'src/app/toast/toast.service';
import { Invoice } from '../../classes/Invoice';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss'],
})
export class InvoiceComponent implements OnInit {
  constructor(
    private invoiceService: InvoiceService,
    private toastService: ToastService
  ) {}

  originalInvoices: Invoice[] = [];
  invoices: Invoice[] = [];

  search: string = '';

  ngOnInit(): void {
    this.invoiceService.getAll().subscribe(
      (data: Invoice[]) => {
        this.invoices = data;
      },
      (err) => {
        this.toastService.show(err.error.message, { error: true });
      }
    );
  }

  filter() {}

  createInvoice() {}

  calculateAmountExBtw(line: InvoiceLine) {
    let price = line.customArticlePrice
      ? line.customArticlePrice
      : line.article!.price;
    let subtotal = line.amount * price;
    if (line.discountPercentage) {
      subtotal = subtotal * (1 - line.discountPercentage / 100);
    }
    return subtotal;
  }

  calculateTotalExBtw(invoice: Invoice) {
    let subtotal = 0;
    invoice.lines.forEach((l) => (subtotal += this.calculateAmountExBtw(l)));

    return subtotal;
  }

  calculateTotalInclBtw(invoice: Invoice) {
    let subtotal = 0;
    invoice.lines.forEach((l) => {
      let totalExBtw = this.calculateAmountExBtw(l);
      subtotal += totalExBtw * (1 + l.article!.btwPercentage / 100);
    });

    return subtotal;
  }

  printInvoice(id: string) {
    this.invoiceService.downloadInvoice(id).subscribe(
      (pdf) => {
        const newBlob = new Blob([pdf], { type: 'application/pdf' });
        const fileName = id + '.pdf';

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(newBlob, fileName);
          return;
        }

        const data = window.URL.createObjectURL(newBlob);
        window.open(data, '_blank');
      },
      (err) => {
        this.toastService.show(err.error.message, { error: true });
      }
    );
  }
}
