package upm.tfg.b190222.games_service.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import upm.tfg.b190222.games_service.entity.Game;

@Getter
@Setter
public class SearchResponse {
    
    private List<Game> games;
    private String response;

    public SearchResponse(List<Game> games, String response){
        this.games = games;
        this.response = response;
    }

}
