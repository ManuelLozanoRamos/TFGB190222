import { Game } from "./game";

export class SearchResponse {
    games:Game[];
    response:string;

    constructor(games:Game[], response:string){
        this.games = games;
        this.response = response;
    }
}
