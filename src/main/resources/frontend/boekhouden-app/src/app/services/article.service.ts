import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Article } from '../classes/Article';
import { Category } from '../classes/Category';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private categories?: Category[];
  private units?: string[];

  constructor(private http: HttpClient) {}

  getAll(): Observable<Article[]> {
    return this.http
      .get<Article[]>(`${environment.apiUrl}/article`)
      .pipe(catchError((err) => throwError(err)));
  }

  updateArticle(updatedArticle: Article): Observable<Article> {
    return this.http
      .put<Article>(`${environment.apiUrl}/article`, updatedArticle)
      .pipe(catchError((err) => throwError(err)));
  }

  createArticle(updatedArticle: Article): Observable<Article> {
    return this.http
      .post<Article>(`${environment.apiUrl}/article`, updatedArticle)
      .pipe(catchError((err) => throwError(err)));
  }

  async getAllCategories(): Promise<Category[]> {
    if (!this.categories) {
      let promise = await this.http
        .get<Category[]>(`${environment.apiUrl}/article/categories`)
        .pipe(catchError((err) => throwError(err)))
        .toPromise();

      this.categories = promise;
    }

    return this.categories;
  }

  async getAllUnits(): Promise<string[]> {
    if (!this.units) {
      let promise = await this.http
        .get<string[]>(`${environment.apiUrl}/article/units`)
        .pipe(catchError((err) => throwError(err)))
        .toPromise();

      this.units = promise;
    }

    return this.units;
  }
}
