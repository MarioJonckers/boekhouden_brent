import {Component, TemplateRef} from '@angular/core';

import { ToastService } from './toast.service';

@Component({
  selector: 'app-toasts',
  template: `
    <ngb-toast
      *ngFor="let toast of toastService.toasts"
      [class]="toast.classname || 'bg-success text-light'"
      class="fade"
      [autohide]="true"
      [delay]="toast.delay || 5000"
      (hidden)="toastService.remove(toast)"
    >
      <ng-template [ngIf]="isTemplate(toast)" [ngIfElse]="text">
        <ng-template [ngTemplateOutlet]="toast.textOrTpl"></ng-template>
      </ng-template>

      <ng-template #text>
          <p class="text">{{ toast.textOrTpl || 'Oeps, er ging iets mis, probeer later opnieuw.' }}</p>
      </ng-template>
    </ngb-toast>
  `,
  styles: [
    '.text { margin: 0 }'],
  host: {'[class.ngb-toasts]': 'true'}
})
export class ToastsContainer {
  constructor(public toastService: ToastService) {}

  isTemplate(toast: any) { return toast.textOrTpl instanceof TemplateRef; }
}
