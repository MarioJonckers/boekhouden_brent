import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'pad' })
export class Pad implements PipeTransform {
  transform(num: number, size: number = 2): string {
    let s: string = num + '';
    while (s.length < size) s = '0' + s;
    return s;
  }
}

@Pipe({ name: 'round' })
export class Round implements PipeTransform {
  transform(num: number, roundTo: number = 2): string {
    return num.toFixed(roundTo).toString();
  }
}

@Pipe({ name: 'randomizeUrl' })
export class RandomizeUrl implements PipeTransform {
  transform(url: string): string {
    return (
      url +
      '?' +
      Math.random()
        .toString(36)
        .replace(/[^a-z]+/g, '')
        .substr(0, 8)
    );
  }
}

@Pipe({ name: 'numberArray' })
export class NumberArray implements PipeTransform {
  transform(number: number): Array<number> {
    const arr = [];
    let index = 0;
    while (index < number) {
      arr.push(index++);
    }
    return arr;
  }
}

@Pipe({ name: 'sort' })
export class SortArray implements PipeTransform {
  transform(items: any[], sortBy?: string): any[] {
    return items.sort((a, b) => {
      if (sortBy) {
        return a[sortBy] - b[sortBy];
      } else {
        return a - b;
      }
    });
  }
}
