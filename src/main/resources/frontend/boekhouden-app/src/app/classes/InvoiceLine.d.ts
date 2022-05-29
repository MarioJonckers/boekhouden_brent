import { Article } from "./Article";

export class InvoiceLine {
    id: number;
    customArticleDescription: string | null;
    orderInDocument: number;
    article?: Article;
    customArticlePrice: number | null;
    amount: number;
    discountPercentage: number;
}