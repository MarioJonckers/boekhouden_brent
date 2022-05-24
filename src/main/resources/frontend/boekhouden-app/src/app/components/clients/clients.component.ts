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
import { Client } from 'src/app/classes/Client';
import { ClientService } from 'src/app/services/client.service';
import { ToastService } from 'src/app/toast/toast.service';

export type SortColumn = keyof Client | '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: { [key: string]: SortDirection } = {
  asc: 'desc',
  desc: '',
  '': 'asc',
};

export interface SortEvent {
  column: SortColumn;
  direction: SortDirection;
}

@Directive({
  selector: 'th[sortable]',
  host: {
    '[class.asc]': 'direction === "asc"',
    '[class.desc]': 'direction === "desc"',
    '(click)': 'rotate()',
  },
})
export class NgbdSortableHeader {
  @Input() sortable: SortColumn = 'id';
  @Input() direction: SortDirection = 'asc';
  @Output() sort = new EventEmitter<SortEvent>();

  rotate() {
    this.direction = rotate[this.direction];
    this.sort.emit({ column: this.sortable, direction: this.direction });
  }
}

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
})
export class ClientsComponent implements OnInit {
  @ViewChildren(NgbdSortableHeader) headers!: QueryList<NgbdSortableHeader>;

  originalClients: Client[] = [];
  clients?: Client[];
  search: string = '';
  selectedClient?: Client;

  currentSorting: SortEvent = { column: 'name', direction: 'asc' };

  constructor(
    private clientService: ClientService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.clientService.getAll().subscribe((data: any) => {
      this.originalClients = data;
      this.onSort({ column: 'name', direction: 'asc' });
    });
  }

  onSort({ column, direction }: SortEvent, keyPress?: KeyboardEvent) {
    this.currentSorting = { column, direction };
    // resetting other headers
    this.headers.forEach((header) => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    // sorting countries
    if (direction === '' || column === '') {
      this.clients = this.originalClients;
    } else {
      this.clients = [...this.originalClients].sort((a, b) => {
        let v1 = a[column];
        let v2 = b[column];

        const res = v1 < v2 ? -1 : v1 > v2 ? 1 : 0;
        return direction === 'asc' ? res : -res;
      });
      this.filterClients(keyPress);
    }
  }

  filterClients(keyPress?: KeyboardEvent) {
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
    this.clients = this.clients?.filter((c: any) => {
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

  updateClient(updatedClient: Client) {
    if (this.selectedClient) {
      this.clientService.updateClient(updatedClient).subscribe(
        (data: Client) => {
          let index = this.originalClients.findIndex((c) => c.id === data.id);
          if (index >= 0) {
            this.originalClients[index] = { ...data };

            if (this.clients) {
              index = this.clients.findIndex((c) => c.id === data.id);
              this.clients[index] = { ...data };
            }
          }

          this.selectedClient = undefined;
          this.toastService.show('Klant is opgeslagen.');
        },
        (err) => {
          this.toastService.show(err.error.message, { error: true });
        }
      );
    }
  }

  copy(client: Client): Client {
    return { ...client };
  }
}
