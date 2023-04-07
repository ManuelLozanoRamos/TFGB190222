import { Game } from "./game";

export class SearchByIdResponse {
    game:Game;
    response:string;

    constructor(game:Game, response:string){
        this.game = game;
        this.response = response;
    }
}
