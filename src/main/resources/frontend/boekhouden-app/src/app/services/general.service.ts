import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GeneralService {
  constructor(private http: HttpClient) {}

  getTest() {
    return this.http
      .get(`${environment.apiUrl}`, { responseType: 'text' })
      .pipe(catchError((err) => throwError(err)));
  }
}
