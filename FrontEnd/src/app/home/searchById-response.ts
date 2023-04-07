import { Review } from "./review";

export class SearchByIdResponse {
    review:Review;
    response:string;

    constructor(review:Review, response:string){
        this.review = review;
        this.response = response;
    }
}
