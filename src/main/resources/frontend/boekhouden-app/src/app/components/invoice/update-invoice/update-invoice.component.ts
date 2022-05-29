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

@Injectable()
export class CustomAdapter extends NgbDateAdapter<Date> {
  readonly DELIMITER = '-';

  fromModel(value: Date | null): NgbDateStruct | null {
    if (value) {
      return {
        day: value.getDate(),
        month: value.getMonth(),
        year: value.getFullYear(),
      };
    }
    return null;
  }

  toModel(date: NgbDateStruct | null): Date | null {
    return date ? new Date(date.year, date.month, date.day) : null;
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
    private modalService: NgbModal
  ) {}

  articles: Article[] = [];
  clients: Client[] = [];
  selectedClientId: number = -1;
  selectedClient: Client | null = null;

  invoice: Invoice = {
    id: -1,
    client: null,
    docDate: new Date(),
    expireDate: new Date(),
    lines: [],
    notes: '',
    paymentMethod: '',
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

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      if (params.id) {
        console.log(params.id);
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
        //TODO remove
        this.invoice.lines.push({
          id: 1,
          customArticleDescription: null,
          orderInDocument: 1,
          customArticlePrice: 1,
          article: this.articles[0],
          amount: 1,
          discountPercentage: 0,
        });
        this.invoice.lines.push({
          id: 2,
          customArticleDescription: 'Custom desc',
          orderInDocument: 0,
          customArticlePrice: null,
          article: this.articles[1],
          amount: 2,
          discountPercentage: 25,
        });
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
    console.log(this.selectedClient);
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
    docDate.setDate(this.invoice.docDate.getDate() + 30);
    this.invoice.expireDate = new Date(docDate.getTime());
  }

  updateLine() {}

  copy(line: InvoiceLine) {
    return { ...line };
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
}
