import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Article } from 'src/app/classes/Article';
import { Category } from 'src/app/classes/Category';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-update-article',
  templateUrl: './update-article.component.html',
  styleUrls: ['./update-article.component.scss'],
})
export class UpdateArticleComponent implements OnInit {
  @Input()
  public article!: Article;
  @Output() exit = new EventEmitter();
  @Output() update = new EventEmitter<Article>();

  categories: Category[] = [];
  units: string[] = [];

  btwPercentages = [0, 6, 12, 21];

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    let categoriesPromise = this.articleService.getAllCategories();
    let unitPromise = this.articleService.getAllUnits();

    categoriesPromise.then(
      (data: Category[]) => {
        this.categories = data;
      },
      (reason) => {
        console.log(reason);
      }
    );
    unitPromise.then(
      (data: string[]) => {
        this.units = data;
      },
      (reason) => {
        console.log(reason);
      }
    );
  }

  updateDescription($event: Event) {
    console.log($event);
  }

  updateCategory() {
    let filteredCategories = this.categories.filter(
      (c) => c.id == this.article.category.id
    );

    if (filteredCategories.length == 1) {
      this.article.category.id = filteredCategories[0].id;
      this.article.category.name = filteredCategories[0].name;
    }
  }
}
