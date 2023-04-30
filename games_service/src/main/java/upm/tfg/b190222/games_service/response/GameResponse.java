package upm.tfg.b190222.games_service.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.games_service.entity.Game;

@Getter
@Setter
public class GameResponse {
    
    private String response;
    private Game game;
    private List<Game> games;

    public GameResponse(String response, Game game, List<Game> games){
        this.response = response;
        this.game = game;
        this.games = games;
    }

}
