import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private salutations?: string[];

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http
      .get(`${environment.apiUrl}/client`)
      .pipe(catchError((err) => throwError(err)));
  }

  async getSalutations(): Promise<string[]> {
    if (!this.salutations) {
      let promise = await this.http
        .get<string[]>(`${environment.apiUrl}/client/salutations`)
        .pipe(catchError((err) => throwError(err)))
        .toPromise();

      this.salutations = promise;
    }

    return this.salutations;
  }
}
