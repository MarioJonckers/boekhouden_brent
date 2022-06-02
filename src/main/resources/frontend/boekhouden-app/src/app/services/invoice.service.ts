import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Invoice } from '../classes/Invoice';

@Injectable({
  providedIn: 'root',
})
export class InvoiceService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Invoice[]> {
    return this.http
      .get<Invoice[]>(`${environment.apiUrl}/document/invoices`)
      .pipe(catchError((err) => throwError(err)));
  }

  get(id: number): Observable<Invoice> {
    return this.http
      .get<Invoice>(`${environment.apiUrl}/document/invoices/${id}`)
      .pipe(catchError((err) => throwError(err)));
  }

  createInvoice(invoice: Invoice): Observable<Invoice> {
    return this.http
      .post<Invoice>(`${environment.apiUrl}/document/invoices`, invoice)
      .pipe(catchError((err) => throwError(err)));
  }

  updateInvoice(invoice: Invoice): Observable<Invoice> {
    return this.http
      .put<Invoice>(`${environment.apiUrl}/document/invoices`, invoice)
      .pipe(catchError((err) => throwError(err)));
  }
}
