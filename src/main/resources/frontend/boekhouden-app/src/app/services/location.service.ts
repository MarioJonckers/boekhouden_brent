import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { City } from '../classes/City';
import { Country } from '../classes/Country';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  private countries?: Country[];
  private cities: Map<string, City[]> = new Map();

  constructor(private http: HttpClient) {}

  async getAllCountries(): Promise<Country[]> {
    if (!this.countries) {
      let promise = await this.http
        .get<Country[]>(`${environment.apiUrl}/location/country`)
        .pipe(catchError((err) => throwError(err)))
        .toPromise();
      this.countries = promise;
    }

    return this.countries;
  }

  async getAllCities(country: string): Promise<City[]> {
    if (!this.cities.has(country)) {
      let promise = await this.http
        .get<City[]>(`${environment.apiUrl}/location/city/${country}`)
        .pipe(catchError((err) => throwError(err)))
        .toPromise();
      this.cities.set(country, promise);
    }

    let cities = this.cities.get(country);
    return cities ? cities : [];
  }
}
