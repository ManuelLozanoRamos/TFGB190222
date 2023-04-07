import { Review } from "./review";

export class SearchResponse {
    reviews:Review[];
    response:string;

    constructor(reviews:Review[], response:string){
        this.reviews = reviews;
        this.response = response;
    }
}
