import { Injectable, TemplateRef } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  toasts: any[] = [];

  show(
    textOrTpl: string | TemplateRef<any>,
    options: { error: boolean; classname?: string } = { error: false }
  ) {
    if (options.error) {
      options.classname = 'bg-danger text-light';
    }
    this.toasts.push({ textOrTpl, ...options });
  }

  remove(toast: any) {
    this.toasts = this.toasts.filter((t) => t != toast);
  }
}
