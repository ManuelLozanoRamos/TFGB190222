package upm.tfg.b190222.games_service.response;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.games_service.entity.Game;

@Getter
@Setter
public class SearchByIdResponse {
    
    private Game game;
    private String response;

    public SearchByIdResponse(Game game, String response){
        this.game = game;
        this.response = response;
    }

}
