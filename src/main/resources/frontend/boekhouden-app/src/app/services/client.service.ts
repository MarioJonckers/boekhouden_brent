import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Client } from '../classes/Client';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  constructor(private http: HttpClient) {}

  getAll() {
    return this.http
      .get(`${environment.apiUrl}/client`)
      .pipe(catchError((err) => throwError(err)));
  }
}
