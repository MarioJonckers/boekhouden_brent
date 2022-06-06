import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  NgbDateAdapter,
  NgbDateStruct,
  NgbModal,
} from '@ng-bootstrap/ng-bootstrap';
import { Article } from 'src/app/classes/Article';
import { Client } from 'src/app/classes/Client';
import { Invoice } from 'src/app/classes/Invoice';
import { InvoiceLine } from 'src/app/classes/InvoiceLine';
import { ArticleService } from 'src/app/services/article.service';
import { ClientService } from 'src/app/services/client.service';
import { InvoiceService } from 'src/app/services/invoice.service';
import { ToastService } from 'src/app/toast/toast.service';

@Injectable()
export class CustomAdapter extends NgbDateAdapter<Date> {
  readonly DELIMITER = '-';

  fromModel(value: Date | null): NgbDateStruct | null {
    if (value) {
      console.log(value);

      return {
        day: value.getDate(),
        month: value.getMonth() + 1,
        year: value.getFullYear(),
      };
    }
    return null;
  }

  toModel(date: NgbDateStruct | null): Date | null {
    console.log(date);

    return date
      ? new Date(date.year, date.month - 1, date.day, 2, 0, 0, 0)
      : null;
  }
}

@Component({
  selector: 'app-update-invoice',
  templateUrl: './update-invoice.component.html',
  styleUrls: ['./update-invoice.component.scss'],
  providers: [{ provide: NgbDateAdapter, useClass: CustomAdapter }],
})
export class UpdateInvoiceComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private invoiceService: InvoiceService,
    private clientService: ClientService,
    private articleService: ArticleService,
    private modalService: NgbModal,
    private toastService: ToastService
  ) {}

  articles: Article[] = [];
  clients: Client[] = [];
  selectedClientId: number = -1;
  selectedClient: Client | null = null;

  invoice: Invoice = {
    id: 'NIEUWE FCTR',
    client: null,
    docDate: new Date(),
    expireDate: new Date(),
    lines: [],
    notes: '',
    paymentMethod: 'FCTR',
    paid: false,
  };

  paymentMethods: { [key: string]: number } = {
    FCTR: 30,
    FCT: 15,
    FCTR_6: 60,
  };

  selectedLine: InvoiceLine | null = null;
  emptyLine: InvoiceLine = {
    id: -1,
    customArticleDescription: null,
    orderInDocument: 0,
    customArticlePrice: null,
    amount: 0,
    discountPercentage: 0,
  };
  selectedArticleId = -1;

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      if (params.id) {
        console.log(params.id);
        if (params.id != 'nieuw') {
          this.invoiceService.get(params.id).subscribe(
            (data: Invoice) => {
              data.docDate = new Date(data.docDate);
              data.expireDate = new Date(data.expireDate);
              this.selectedClientId = data.client ? data.client.id : -1;
              this.invoice = data;
            },
            (err) => {
              this.toastService.show(err.error.message, { error: true });
              this.router.navigate(['facturen']);
            }
          );
        }
      } else {
        this.router.navigate(['/facturen']);
      }
    });

    this.clientService.getAll().subscribe(
      (clients) => {
        this.clients = clients;
      },
      (err) => {
        console.log(err.error.message);
      }
    );

    this.articleService.getAll().subscribe(
      (articles) => {
        this.articles = articles;
      },
      (err) => {
        console.log(err.error.message);
      }
    );

    this.updateExpireDate();
  }

  updateSelectedClient() {
    for (let i = 0; i < this.clients.length; i++) {
      if (this.clients[i].id == this.selectedClientId) {
        this.selectedClient = this.clients[i];
      }
    }
  }

  chooseClient() {
    console.log(this.selectedClient);

    this.invoice.client = JSON.parse(JSON.stringify(this.selectedClient));
    this.selectedClient = null;
  }

  open(content: any) {
    this.modalService.open(content).result;
  }

  updateExpireDate() {
    let docDate = new Date(this.invoice.docDate.getTime());

    docDate.setDate(
      this.invoice.docDate.getDate() +
        this.paymentMethods[this.invoice.paymentMethod]
    );
    this.invoice.expireDate = new Date(docDate.getTime());
  }

  updateLine() {
    if (this.selectedLine!.id == -1) {
      if (!this.selectedLine!.article) {
        this.toastService.show('Er moet een product worden geselecteerd.', {
          error: true,
        });
        return;
      }
      let newLine = { ...this.selectedLine! };
      newLine.id = 0;
      this.invoice.lines.push(newLine);
      this.sortLines();
    }

    this.modalService.dismissAll('Save click');
    this.selectedLine = null;
  }

  removeLine(order: number) {
    this.invoice.lines = this.invoice.lines.filter(
      (l) => l.orderInDocument != order
    );
    this.sortLines();

    let newOrder = 0;
    this.invoice.lines.forEach((l) => {
      l.orderInDocument = newOrder++;
    });
  }

  copy(line: InvoiceLine) {
    let newLine = { ...line };
    newLine.orderInDocument = this.invoice.lines.length;

    return newLine;
  }

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

  calculateTotalExBtw() {
    let subtotal = 0;
    this.invoice.lines.forEach(
      (l) => (subtotal += this.calculateAmountExBtw(l))
    );

    return subtotal;
  }

  calculateTotalInclBtw() {
    let subtotal = 0;
    this.invoice.lines.forEach((l) => {
      let totalExBtw = this.calculateAmountExBtw(l);
      subtotal += totalExBtw * (1 + l.article!.btwPercentage / 100);
    });

    return subtotal;
  }

  updateProductForLine() {
    if (this.selectedArticleId != -1) {
      let article = this.articles.filter(
        (a) => a.id === this.selectedArticleId
      );

      if (article && article.length > 0) {
        this.selectedLine!.article = { ...article[0] };
      }
    } else {
      this.selectedLine!.article = undefined;
    }
  }

  moveDown(currentOrder: number) {
    if (currentOrder == this.invoice.lines.length - 1) {
      return;
    }

    this.invoice.lines.forEach((l) => {
      if (l.orderInDocument == currentOrder) {
        l.orderInDocument++;
      } else if (l.orderInDocument == currentOrder + 1) {
        l.orderInDocument--;
      }
    });

    this.sortLines();
  }

  moveUp(currentOrder: number) {
    if (currentOrder == 0) {
      return;
    }

    this.invoice.lines.forEach((l) => {
      if (l.orderInDocument == currentOrder) {
        l.orderInDocument--;
      } else if (l.orderInDocument == currentOrder - 1) {
        l.orderInDocument++;
      }
    });

    this.sortLines();
  }

  sortLines() {
    this.invoice.lines.sort((a, b) => a.orderInDocument - b.orderInDocument);
  }

  updateInvoice() {
    if (this.invoice.id == 'NIEUWE FCTR') {
      let invoiceToSend = { ...this.invoice };
      invoiceToSend.id = '0';
      this.invoiceService.createInvoice(invoiceToSend).subscribe(
        (data: Invoice) => {
          this.router.navigate(['facturen/' + data.id]);
          this.toastService.show('Factuur is opgeslagen.');
        },
        (err) => {
          this.toastService.show(err.error.message, { error: true });
        }
      );
    } else {
      this.invoiceService.updateInvoice(this.invoice).subscribe(
        (data: Invoice) => {
          this.router.navigate(['facturen/' + data.id]);
          this.toastService.show('Factuur is opgeslagen.');
        },
        (err) => {
          this.toastService.show(err.error.message, { error: true });
        }
      );
    }
  }

  printInvoice() {
    this.invoiceService.downloadInvoice(this.invoice.id).subscribe(
      (pdf) => {
        const newBlob = new Blob([pdf], { type: 'application/pdf' });

        const data = window.URL.createObjectURL(newBlob);
        window.open(data, '_blank');
      },
      (err) => {
        this.toastService.show(err.error.message, { error: true });
      }
    );
  }
}
