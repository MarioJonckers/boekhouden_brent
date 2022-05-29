import {
  Component,
  Directive,
  EventEmitter,
  Input,
  OnInit,
  Output,
  QueryList,
  ViewChildren,
} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Article } from 'src/app/classes/Article';
import { ArticleService } from 'src/app/services/article.service';
import { ToastService } from 'src/app/toast/toast.service';

export type ArticleSortColumn = keyof Article | '';
export type ArticleSortDirection = 'asc' | 'desc' | '';
const rotate: { [key: string]: ArticleSortDirection } = {
  asc: 'desc',
  desc: '',
  '': 'asc',
};

export interface ArticleSortEvent {
  column: ArticleSortColumn;
  direction: ArticleSortDirection;
}

@Directive({
  selector: 'th[articleSortable]',
  host: {
    '[class.asc]': 'direction === "asc"',
    '[class.desc]': 'direction === "desc"',
    '(click)': 'rotate()',
  },
})
export class NgbdArticleSortableHeader {
  @Input() articleSortable: ArticleSortColumn = 'id';
  @Input() direction: ArticleSortDirection = 'asc';
  @Output() sort = new EventEmitter<ArticleSortEvent>();

  rotate() {
    this.direction = rotate[this.direction];
    this.sort.emit({
      column: this.articleSortable,
      direction: this.direction,
    } as ArticleSortEvent);
  }
}

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss'],
})
export class ArticlesComponent implements OnInit {
  @ViewChildren(NgbdArticleSortableHeader)
  headers!: QueryList<NgbdArticleSortableHeader>;

  originalArticles: Article[] = [];
  articles?: Article[];
  search: string = '';
  selectedArticle?: Article;
  newCategory: string = '';

  emptyArticle: Article = {
    id: -1,
    name: '',
    description: '',
    category: {
      id: -1,
      name: '',
    },
    price: 0,
    btwPercentage: 21,
    unit: null,
    notes: '',
  };

  // regex = /\n/g;
  currentSorting: ArticleSortEvent = { column: 'name', direction: 'asc' };

  constructor(
    private articleService: ArticleService,
    private toastService: ToastService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.articleService.getAll().subscribe((data: any) => {
      this.originalArticles = data;
      this.originalArticles.forEach((a) => {
        if (a.description) {
          a.description = a.description.replace(/\\n/g, '\n');
        }
        if (!a.category) {
          a.category = { id: -1, name: '' };
        }
      });
      this.onSort({ column: 'name', direction: 'asc' });
    });
  }

  onSort({ column, direction }: ArticleSortEvent, keyPress?: KeyboardEvent) {
    this.currentSorting = { column, direction };
    // resetting other headers
    this.headers.forEach((header) => {
      if (header.articleSortable !== column) {
        header.direction = '';
      }
    });

    // sorting countries
    if (direction === '' || column === '') {
      this.articles = this.originalArticles;
    } else {
      this.articles = [...this.originalArticles].sort((a, b) => {
        let v1 = a[column];
        let v2 = b[column];
        if (!v1) {
          v1 = '';
        }
        if (!v2) {
          v2 = '';
        }

        const res = v1 < v2 ? -1 : v1 > v2 ? 1 : 0;
        return direction === 'asc' ? res : -res;
      });
      this.filterArticles(keyPress);
    }
  }

  filterArticles(keyPress?: KeyboardEvent) {
    let search = this.search;
    if (
      keyPress &&
      keyPress.key &&
      'abcdefghijklmopqrstuvwxyz'.includes(keyPress.key.toLowerCase())
    ) {
      search += keyPress.key;
      console.log(search);
    } else if (keyPress && keyPress.key && 'Backspace' == keyPress.key) {
      if (search && search.length > 0) {
        search = search.substring(0, search.length - 1);
      }
    }
    this.articles = this.articles?.filter((c: any) => {
      var values = ['name', 'address', 'address2', 'tel', 'email', 'mobile'];
      var flag = false;

      values.forEach((val) => {
        if (c[val] && c[val].toLowerCase().includes(search.toLowerCase())) {
          flag = true;
          return;
        }
      });
      if (c.city && c.city.postalCode && c.city.postalCode.includes(search)) {
        flag = true;
      }
      if (c.city && c.city.city && c.city.city.includes(search)) {
        flag = true;
      }
      return flag;
    });
  }

  updateArticle(updatedArticle: Article) {
    if (this.selectedArticle) {
      if (this.selectedArticle.id == -1) {
        this.articleService.createArticle(updatedArticle).subscribe(
          (data: Article) => {
            this.handleResponse(data);
            this.onSort(this.currentSorting);
          },
          (err) => {
            this.toastService.show(err.error.message, { error: true });
          }
        );
      } else {
        this.articleService.updateArticle(updatedArticle).subscribe(
          (data: Article) => {
            this.handleResponse(data);
          },
          (err) => {
            this.toastService.show(err.error.message, { error: true });
          }
        );
      }
    }
  }

  handleResponse(data: Article) {
    if (this.selectedArticle) {
      let index = this.originalArticles.findIndex((c) => c.id === data.id);
      if (index >= 0) {
        this.originalArticles[index] = { ...data };

        if (this.articles) {
          index = this.articles.findIndex((c) => c.id === data.id);
          this.articles[index] = { ...data };
        }
      } else {
        this.originalArticles.push(data);
        if (this.articles) {
          this.articles.push(data);
        }
      }

      this.selectedArticle = undefined;
      this.toastService.show('Product is opgeslagen.');
    }
  }

  copy(article: Article): Article | undefined {
    if (article.id == this.selectedArticle?.id) {
      return undefined;
    }
    return { ...article };
  }

  saveCategory() {
    this.articleService.createCategory(this.newCategory).then(() => {
      this.newCategory = '';
    });
  }

  open(content: any) {
    this.modalService.open(content).result;
  }
}
