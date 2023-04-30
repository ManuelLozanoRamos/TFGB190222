import { Review } from "../reviews/review";

export class ReviewResponse {
    response:string;
    review:Review;
    reviews:Review[];

    constructor(response:string, review:Review, reviews:Review[]){
        this.response = response;
        this.review = review;
        this.reviews = reviews;
    }
}
