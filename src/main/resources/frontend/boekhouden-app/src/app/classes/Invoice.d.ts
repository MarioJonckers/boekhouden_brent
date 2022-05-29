import { Client } from "./Client";
import { InvoiceLine } from "./InvoiceLine";

export class Invoice {
  id: number;
  docDate: Date;
  expireDate: Date;
  client: Client | null;
  lines: InvoiceLine[];
  paymentMethod: string;
  notes: string;
}
