import { Game } from "../games/game";

export class GameResponse {
    response:string;
    game:Game;
    games:Game[];

    constructor(response:string, game:Game, games:Game[]){
        this.response = response;
        this.game = game;
        this.games = games;
    }
}
