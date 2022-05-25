import { Category } from "./Category";

export class Article {
    id: number;
    name: string;
    description: string;
    category: Category;
    price: number;
    btwPercentage: number;
    unit: string;
    notes: string;
}